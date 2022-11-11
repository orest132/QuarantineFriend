import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Inject, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Hobby, UserModel } from 'src/app/model/userModel';
import { UserService } from 'src/app/services/user.service';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, AfterViewInit {
  @ViewChild('input') input: ElementRef;

  userFormGroup: FormGroup;
  user: UserModel;
  subscription: Subscription;
  id: number = -1;
  hide = true;
  password: string;
  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  hobbies: Hobby[];
  temp:number=0;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private ElByClassName: ElementRef,
    private CD: ChangeDetectorRef
  ) {}
  ngAfterViewInit(): void {
    
  }
  ngOnChanges(changes: SimpleChanges): void {}

  clicked(){
      let hobby:Hobby = new Hobby();
      hobby.hobby = this.input.nativeElement.value
      this.userService.addHobby(hobby).subscribe(response => {
        this.hobbies.push(response);
        this.ngOnInit();
      })

  }

  ngDoCheck() {
    this.CD.detectChanges();
  }

  ngOnInit(): void {
    this.refreshFormGroup();
    
    this.userService.getHobbies().subscribe(response =>{
      this.hobbies = response;
      console.log(response);
    })
  }

  refreshFormGroup() {
    this.userFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        firstName: new FormControl(``, [
          Validators.required,
          // Validators.minLength(2),
        ]),
        lastName: new FormControl(``, [
          Validators.required,
          Validators.minLength(2),
        ]),
        username: new FormControl(``, [
          Validators.required,
        ]),
        email: new FormControl(``, [
          Validators.required,
          Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$'),
        ]),
        password: new FormControl(
        ),
        confirmPassword: new FormControl(
          ),
        hobbies: new FormControl(
        ),
        age: new FormControl(
        ),
      }),
    });
  }

  onSubmit() {
    let firstName = this.userFormGroup.value.user.firstName;
    let lastName = this.userFormGroup.value.user.lastName;
    let email = this.userFormGroup.value.user.email;
    let age = this.userFormGroup.value.user.age;
    let password = this.userFormGroup.value.user.password;
    let confirmPassword = this.userFormGroup.value.user.confirmPassword;
    let username = this.userFormGroup.value.user.username;
    let hobbiess = this.userFormGroup.value.user.hobbies;
    var userHobbies: Array<Hobby>=[];
    var userHobby: Hobby;
    if(hobbiess!=null){
    for(var hobbyy of this.hobbies){
      <number[]>hobbiess.forEach(element => {
        if(element==hobbyy.id){
          userHobby = new Hobby();
          userHobby.id = hobbyy.id;
          userHobby.hobby=hobbyy.hobby;
          userHobbies.push(userHobby);
        }
      });
    }
  }
      
    let theUser: UserModel = new UserModel(
      firstName,
      lastName,
      email,
      age,
      username,
      userHobbies
    );
    // this.userService.addUser(theUser).subscribe((response) => {
    //   this.router.navigate['/users'];
    // });
    if(password===confirmPassword){
      this.userService.addUser(theUser, password).subscribe((response) => {
        this.router.navigateByUrl('/login');
      });
    }
    else
      console.log("Password mismatch");
  }

}
