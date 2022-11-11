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
  selector: 'app-edit-password',
  templateUrl: './edit-password.component.html',
  styleUrls: ['./edit-password.component.css'],
})
export class EditPasswordComponent implements OnInit {
  password: string;
  passwordConfirmation: string;
  form = this.fb.group({
    password: ['', [Validators.required, Validators.maxLength(60)]],
    passwordConfirmation: ['', Validators.required],
  });
  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private user: UserModel,
    private dialogRef: MatDialogRef<EditPasswordComponent>,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  editPassword() {
    let user: UserModel = JSON.parse(localStorage.getItem('user'));
    this.userService
      .resetPassowrd(user.id, this.form.value.password)
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
      this.form.controls['password'] !==
      this.form.controls['passwordConfirmation']
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

export function openEditPasswordDialog(dialog: MatDialog, user: UserModel) {
  const config = new MatDialogConfig();
  config.disableClose = true;
  config.autoFocus = true;
  config.data = {
    ...user,
  };
  const dialogRef = dialog.open(EditPasswordComponent, config);
  return dialogRef.afterClosed();
}
