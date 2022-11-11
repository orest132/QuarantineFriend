import { UserService } from './../../services/user.service';
import { ChatService } from './../../services/chat.service';
// import { MessageService } from './../../services/message.service';
import { ChatModel, Message, UserModel,  } from './../../model/userModel';
import {
  Component,
  ElementRef,
  OnInit,
  ViewChild,
  AfterViewInit,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
})
export class ChatComponent implements OnInit {
  @ViewChild('input') input: ElementRef;
  @ViewChild('overwrap') overwrap: ElementRef;
  @ViewChild('scrollMe') private myScrollContainer: ElementRef;

  messages: Message[];
  chat: ChatModel;
  myForm: FormGroup;
  userLoggedIn: UserModel;
  eventSource: EventSource;
  open: boolean = true;
  msg: Message;

  constructor(
    private chatService: ChatService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService
  ) {
    this.userService.eventSource.subscribe((response) => {
      this.eventSource = response;
    });
    this.eventSource.addEventListener('sendMessage', (event) => {
      this.msg = JSON.parse(event.data);
      this.messages.push(this.msg);
      this.scrollToBottom();
    });
  }

  ngAfterViewChecked() {        
    this.scrollToBottom();        
}

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.userLoggedIn = response;
    });
    this.refreshData();
    
  }

  ngOnDestroy(): void {
  }

  refreshData() {
    this.activatedRoute.paramMap.subscribe((paramMap) => {
      let id = +paramMap.get('chatId');
      this.chatService.getChatById(id).subscribe(response =>{
        this.chat=response;
        this.messages=this.chat.messages;
      })
    });
  }

  scrollToBottom(): void {
    try {
      this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
    } catch (err) {}
  }

  onClick() {
    var messagetemp = this.input.nativeElement.value;
    var message = new Message();
    message.message = messagetemp;
    message.user = this.userLoggedIn;
    this.messageService
      .postMessageToChat(this.chat.id, message)
      .subscribe(() => {
        this.scrollToBottom();
      });
    this.input.nativeElement.value = '';
  }

  openCloseChat() {
    this.open = !this.open;
    // if (this.overwrap.nativeElement.style.display == 'none')
    //   this.overwrap.nativeElement.style.display = 'block';
    // else this.overwrap.nativeElement.style.display = 'none';
    // this.scrollToBottom();
  }
}
