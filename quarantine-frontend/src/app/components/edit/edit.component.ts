import { FileService } from './../../services/file.service';
import { MatDialog } from '@angular/material/dialog';
import { Hobby, UserModel } from './../../model/userModel';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { openEditPasswordDialog } from '../edit-password/edit-password.component';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css'],
})
export class EditComponent implements OnInit {
  user: UserModel;
  // firstName: string;
  // lastName: string;
  // email: string;
  // jobPosition: string;
  // username: string;
  selectedFiles?: FileList;
  currentFile?: File;
  message = '';
  errorMsg = '';
  userFormGroup: FormGroup;
  username: string;
  userLoggedIn: UserModel;

  constructor(
    private userService: UserService,
    private router: Router,
    private dialog: MatDialog,
    private fileService: FileService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.username = params['username'];
      this.userService.userLoggedIn.subscribe((response) => {
        this.userLoggedIn = response;
        this.userService.getUserByUsername(this.username).subscribe((user) => {
          this.user = user;
        });
      });
        
    });
  }

  

  refreshUser() {
    this.userService.getUserByUsername(this.username).subscribe((user) => {
      this.user = user;
    });
  }

  

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  upload(): void {
    this.errorMsg = '';

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.fileService.upload(this.user.id, this.currentFile).subscribe(
          (event: any) => {
            this.userService.userLoggedIn.next(this.user);

            if (event.type === HttpEventType.UploadProgress) {
            } else if (event instanceof HttpResponse) {
              this.message = event.body.responseMessage;
            }
            this.userService.userLoggedIn.next(this.user);
          },
          (err: any) => {
            console.log(err);

            if (err.error && err.error.responseMessage) {
              this.errorMsg = err.error.responseMessage;
            } else {
              this.errorMsg = 'Error occurred while uploading a file!';
            }
            this.currentFile = undefined;
          }
        );
        this.userService.userLoggedIn.next(this.user);
      }
      this.router.navigate(['/profile']);
      this.refreshUser();
      this.userService.userLoggedIn.next(this.user);

      this.selectedFiles = undefined;
    }
  }

  resetPassword() {
    openEditPasswordDialog(this.dialog, this.user).subscribe();
  }
}
