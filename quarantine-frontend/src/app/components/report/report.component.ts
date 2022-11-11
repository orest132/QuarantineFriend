import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ChatModel, ReportModel, UserModel } from 'src/app/model/userModel';
import { ChatService } from 'src/app/services/chat.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  userFormGroup: FormGroup;
  chat: ChatModel;
  userLoggedIn: UserModel;
  userReporting: UserModel;
  report: ReportModel;


  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private chatService: ChatService,
    private router: Router
    
  ) { }

  getChat() {
    this.activatedRoute.paramMap.subscribe((paramMap) => {
      let id = +paramMap.get('chatId');
      this.chatService.getChatById(id).subscribe(response =>{
        this.chat=response;
        if(this.chat.user1.id==this.userLoggedIn.id)
          this.userReporting=this.chat.user2;
        else
          this.userReporting=this.chat.user1;

          this.buildForm();
      })
    });
  }

  
  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.userLoggedIn = response;
      this.getChat();
    });
    
  }

  buildForm() {
    this.userFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        report: new FormControl(),
      }),
    });
  }

  onSubmit() {
    let r = this.userFormGroup.value.user.report;
    this.report = new ReportModel();
    this.report.reporter = this.userLoggedIn;
    this.report.reportee = this.userReporting;
    this.report.report = r;
    this.userService.report(this.report).subscribe();
    this.router.navigate([`/users`]);
  }

}
