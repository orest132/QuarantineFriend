import { Component, OnInit } from '@angular/core';
import { ThinRenderTargetTexture } from '@babylonjs/core';
import { RequestModel, UserModel } from 'src/app/model/userModel';
import { RequestService } from 'src/app/services/request.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {
  requests: RequestModel[];
  userLoggedIn: UserModel;
  displayColumns = ['FirstName','LastName', 'UserName', 'Mark As Read'];

  constructor(
    private userService: UserService,
    private requestService: RequestService,
  ) { }

  ngOnInit(): void {
    this.userService.userLoggedIn.subscribe((response) => {
      this.userLoggedIn = response;
      this.loadRequests();
    });
  }

  loadRequests(){
    this.requestService.getRequestsByUserId(this.userLoggedIn.id).subscribe((response) =>{
      this.requests = response;
    })
  }

  acceptRequest(id: number){
    this.requestService.delete(id).subscribe(response=>{
      this.loadRequests();
    });
    
  }



}
