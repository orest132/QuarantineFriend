import { AlertComponent } from './../alert/alert.component';
import { UserModel } from './../../model/userModel';
import { AuthService } from './../../services/auth.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
// import { UserService } from './../../services/user.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';
import { openEditPasswordDialog } from '../forget-password/forget-password.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private dialog: MatDialog,
    private router: Router,
    // private userService: UserService,
    private authService: AuthService,
    private _snackBar: MatSnackBar,
    private userService: UserService
  ) {}
  username: string;
  password: string;
  invalidLogin: boolean;
  hide = true;
  ngOnInit(): void {}

  handleLogin() {
    this.authService
      .login(this.username, this.password)
      .subscribe((response: HttpResponse<UserModel>) => {
        localStorage.setItem('user', JSON.stringify(response.body['user']));
        localStorage.setItem('token', response.body['token']);
        this.userService.userLoggedIn.next(response.body['user']);
        this.openSnackBar();
        this.router.navigate(['/users']);
      });
  }

  openSnackBar() {
    this._snackBar.openFromComponent(AlertComponent, {
      duration: 2000,
    });
  }

  forgetPassword() {
    openEditPasswordDialog(this.dialog).subscribe();
  }
}
