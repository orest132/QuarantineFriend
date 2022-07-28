import { Authority, Role, UserModel, RequestModel, Hobby, ReportModel } from '../model/userModel';
import { EventEmitter, Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, map, Observable, Subject } from 'rxjs';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})

export class UserService implements OnInit {
  user = new EventEmitter<UserModel>();
  userRole = new EventEmitter<Role>();
  private token: string;
  private jwtHelper = new JwtHelperService();
  eventSource: Subject<EventSource> = new BehaviorSubject<EventSource>(
    undefined
  );
  userLoggedIn: Subject<UserModel> = new BehaviorSubject<UserModel>(
    JSON.parse(localStorage.getItem('user'))
  );
  loggedInUsername: string;
  theUserLoggedIn: Subject<UserModel> = new BehaviorSubject<UserModel>(null);

  private userEndPoint: string = 'http://localhost:8080/api/users';
  constructor(private httpClient: HttpClient, private router: Router) {}
  ngOnInit(): void {
    // this.userLoggedIn.next(JSON.parse(localStorage.getItem('user')));
  }

  report(report: ReportModel) {
    return this.httpClient.post(`http://localhost:8080/api/report`,report);
  }

  getReports(){
    return this.httpClient.get<ReportModel[]>(`http://localhost:8080/api/reports`);
  }

  deleteReport(id:number){
    return this.httpClient.delete(`http://localhost:8080/api/report/delete/${id}`);
  }

  banReportee(id:number){
    return this.httpClient.put(`http://localhost:8080/api/user/ban/${id}`,"");
  }

  getHobbiesReport(){
    return this.httpClient.get(`http://localhost:8080/api/hobbies/report`);
  }

  getUsersSorted(id:number, numberOfUsers: number){
    return this.httpClient.get<UserModel[]>(`http://localhost:8080/api/users/sorted/${id}/${numberOfUsers}`);
  }

  getUsers(): Observable<UserModel[]> {
    return this.httpClient.get<UserModel[]>(this.userEndPoint);
  }

  // public getUsersPaginated(
  //   page: number,
  //   size: number
  // ): Observable<UserModel[]> {
  //   return this.httpClient.get<UserModel[]>(
  //     `${this.userEndPoint}?page=${page}?size=${size}`
  //   );
  // }

  addHobbyByUserId(hobbie: Hobby, id: number){
    return this.httpClient.post(`http://localhost:8080/api/hobby/user/${id}`, hobbie);
  }

  getUserByUsername(username:string){
    return this.httpClient.get<UserModel>(`http://localhost:8080/api/user/${username}`);
  }

  addHobby(hobbie: Hobby){
    return this.httpClient.post<Hobby>(`http://localhost:8080/api/hobby`, hobbie);
  }

  getHobbies(){
    return this.httpClient.get<Hobby[]>(`http://localhost:8080/api/hobbies`);
  }

  addUser(user: UserModel, password: string) {
    let tempUser: RegisterModel = new RegisterModel();
    tempUser.user = user;
    tempUser.password = password;
    return this.httpClient.post('http://localhost:8080/api/register', tempUser);
  }

  sendRequest(request: RequestModel) {
    return this.httpClient.post(`http://localhost:8080/api/request/send`, request).subscribe();
  }

  deleteUser(id: number) {
    return this.httpClient.delete(`http://localhost:8080/api/user/delete/${id}`);
  }

  updateUser(user: UserModel): Observable<HttpResponse<UserModel>> {
    return this.httpClient.put<HttpResponse<UserModel>>(
      `${this.userEndPoint}/${user.id}`,
      user
    );
  }

  getUserById(id: number): Observable<UserModel> {
    return this.httpClient.get<UserModel>(`${this.userEndPoint}/${id}`);
  }

  login(username: string, password: string) {
    return this.httpClient.post<UserModel>('http://localhost:8080/user/login', {
      username: username,
      password: password,
    });
  }
  saveToken(token: string) {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public isLoggedIn(): boolean {
    this.token = localStorage.getItem('token');
    if (this.token != null && this.token !== '') {
      if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
        if (!this.jwtHelper.isTokenExpired(this.token)) {
          this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        }
      }
    } else {
      this.logout();
      return false;
    }
  }

  logout() {
    localStorage.removeItem('token');
  }

  public resetPassowrd(id: number, password: string) {
    return this.httpClient.put(
      `http://localhost:8080/api/reset-password/${id}`,
      password
    );
  }

  public forgetPassowrd(email: string) {
    return this.httpClient.put(
      `http://localhost:8080/api/forget-password`,
      email
    );
  }
}

class RegisterModel{
  user: UserModel;
  password: string;
}