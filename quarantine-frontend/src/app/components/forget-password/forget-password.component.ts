import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, Validators } from '@angular/forms';
import { UserModel } from './../../model/userModel';
import { UserService } from './../../services/user.service';
import { Component, Inject, OnInit } from '@angular/core';
import {
  MatDialog,
  MatDialogConfig,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { PasswordChanged } from '../alert/alert.component';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css'],
})
export class ForgetPasswordComponent implements OnInit {
  email: string;
  emailConfirmation: string;
  form = this.fb.group({
    email: ['', [Validators.required, Validators.maxLength(60)]],
    emailConfirmation: ['', Validators.required],
  });
  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private user: UserModel,
    private dialogRef: MatDialogRef<ForgetPasswordComponent>,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  sendMail() {
    let user: UserModel = JSON.parse(localStorage.getItem('user'));
    this.userService
      .forgetPassowrd(this.form.value.email)
      .subscribe((response) => {
        this.openSnackBar();
        this.dialogRef.close();
      });
  }
  close() {
    this.dialogRef.close();
  }

  sameFields(): boolean {
    if (
      this.form.controls['eamil'] !==
      this.form.controls['emailConfirmation']
    ) {
      return false;
    }
    return true;
  }

  openSnackBar() {
    this._snackBar.openFromComponent(PasswordChanged, {
      duration: 3000,
    });
  }
}

export function openEditPasswordDialog(dialog: MatDialog) {
  const config = new MatDialogConfig();
  config.disableClose = true;
  config.autoFocus = true;
  const dialogRef = dialog.open(ForgetPasswordComponent, config);
  return dialogRef.afterClosed();
}
