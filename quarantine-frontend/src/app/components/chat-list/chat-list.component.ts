import { Component, OnInit } from '@angular/core';
import { ChatModel, UserModel } from 'src/app/model/userModel';
import { ChatService } from 'src/app/services/chat.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent implements OnInit {
  chats: ChatModel[];
  userLoggedIn: UserModel;
  displayColumns = ['FirstName','LastName', 'UserName', 'Open chat', 'Report','Block user/chat','Is blocked?'];

  constructor(
    private userService: UserService,
    private chatService: ChatService,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.userLoggedIn = response;
      this.loadChats();
    });
  }

    loadChats(){
    this.chatService.getChatByUserId(this.userLoggedIn.id).subscribe((response) =>{
      this.chats = response;
    })
  }

  blockChat(chat:ChatModel, id: number){
    this.chatService.makeChatInactiveByUser(chat,id).subscribe(response=>{
      this.loadChats();
    });
  }
}
