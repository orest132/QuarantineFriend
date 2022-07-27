import { Router } from '@angular/router';
import { UserModel, Role } from '../../model/userModel';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { ngxCsv } from 'ngx-csv/ngx-csv';
@Component({
  selector: 'app-menu',
  templateUrl: './menu-component-material.html',
  styleUrls: ['./menu.component.css'],
})
export class MenuComponent implements OnInit {
  userLoggedIn: UserModel;
  localStorage = localStorage;
  notifications: Notification[] = [];
  eventSource: EventSource;
  reportRows: [
    {
      hobby: string;
      count: number;
    }
  ];

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getHobbiesReport().subscribe();
    var urlEndPoint = 'http://localhost:8080/api/subscribe';
    this.eventSource = new EventSource(urlEndPoint);
    this.userService.eventSource.next(this.eventSource);
    this.eventSource.addEventListener("notification", (event)=>{
      this.refreshNotifications()
    });
    this.eventSource.addEventListener("updateImage", (event)=>{
      this.userService.userLoggedIn.next(
        JSON.parse(this.localStorage.getItem('user'))
      );
    });
    this.userService.userLoggedIn.next(
      JSON.parse(this.localStorage.getItem('user'))
    );
    this.refreshNotifications();
    this.userService.getHobbiesReport().subscribe(
      (
        response: [
          {
            hobby: string;
            count: number;
          }
        ]
      ) => {
        this.reportRows = response;
        console.log(response+"kjo esh");
      }
    ); 
  }

  downloadCsv() {
    let options = {
      fieldSeparator: ',',
      quoteStrings: '"',
      decimalseparator: '.',
      showLabels: true,
      showTitle: true,
      title: `Hobbies report`,
      useBom: true,
      noDownload: false,
      headers: [
        'Hobby',
        'Count',
      ],
    };
    let date: Date = new Date();
    new ngxCsv(this.reportRows, `hobbies/report/${date}`, options);
  }

  refreshNotifications(){
    this.userService.userLoggedIn.subscribe((response) => {
      this.userLoggedIn = response;
    });
  }

  logout() {
    this.localStorage.clear();
    this.router.navigate(['/login']);
    this.userService.userLoggedIn.next(null);
    this.userService.userRole.next(null);
  }
  onClickProfile() {
    console.log('inside on click profile');
    // this.router.navigate[`/edit`];
    this.router.navigateByUrl(`/edit/${this.userLoggedIn.id}`);
  }

  goToRegister(){
    this.router.navigate(['/register'])
  .then(() => {
    window.location.reload();
  });
  }
}
