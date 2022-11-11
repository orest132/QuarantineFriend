import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from './../../services/user.service';
import { UserModel, RequestModel } from '../../model/userModel';
import { Component, OnInit } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { RequestService } from 'src/app/services/request.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  number:number=5;
  userLoggedIn: UserModel;
  users: UserModel[] = [];
  displayColumns = [
    'Avatar',
    'First Name',
    'Last Name',
    'Age',
    'Send Request'
  ];
  loading: boolean = false;
  selection = new SelectionModel<UserModel>(true, []);

  constructor(
    private userService: UserService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.refreshUsers();
  }

  refreshUsersSorted(){
    this.userService.getUsersSorted(this.userLoggedIn.id,this.number).subscribe((response) => {
      this.users = response;
      this.loading=false;
    });
  }

  refreshUsers() {
    this.loading = true;
    this.userService.userLoggedIn.subscribe((response) => {
      this.userLoggedIn = response;
      if(this.userLoggedIn.id==1)
      {
        this.displayColumns.push("Delete");
        this.displayColumns.push("isBanned");
        this.displayColumns.push("Ban");
        this.userService.getUsers().subscribe((response) => {
          this.users = response;
          this.loading=false;
        });
      }
      else{
        this.refreshUsersSorted();
      }
      
    });
    
  }
  sendRequest(user: UserModel){
    var request:RequestModel = new RequestModel();
    request.from_user = this.userLoggedIn;
    request.to_user = user;
    this.userService.sendRequest(request);
  }
  onUserToggled(user: UserModel) {
    this.selection.toggle(user);
  }

  isAllSelected(): boolean {
    return this.selection.selected?.length == this.users.length;
  }

  deleteUser(user:UserModel){
    this.userService.deleteUser(user.id).subscribe(response=>{
      this.refreshUsersSorted();
    });
  }
  banUser(id: number){
    this.userService.banReportee(id).subscribe(response=>{
      this.refreshUsersSorted();
    });
  }

  more(){
    this.number+=5;
    this.refreshUsersSorted();
  }

  toggleAll() {
    if (this.isAllSelected) {
      this.selection.clear();
    } else {
      this.selection.select(...this.users);
    }
  }
}
