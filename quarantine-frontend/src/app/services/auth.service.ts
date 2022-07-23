import { Observable } from 'rxjs';
import { UserModel } from './../model/userModel';
import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  rootUrl: string = 'http://localhost:8080';
  token: string;
  loggedInUsername: string;
  jwtHeloper = new JwtHelperService();
  constructor(private http: HttpClient) {}

  public login(
    username: string,
    password: string
  ): Observable<HttpResponse<UserModel>> {
    return this.http.post<UserModel>(
      `${this.rootUrl}/api/login`,
      { username: username, password: password },
      {
        observe: 'response',
      }
    );
  }
  AccessControl;

  public register(user: UserModel): Observable<UserModel | HttpErrorResponse> {
    return this.http.post<UserModel | HttpErrorResponse>(
      `${this.rootUrl}/api/register`,
      user
    );
  }

  public logout(): void {
    this.token = null;
    this.loggedInUsername = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
  }

  public saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public addUserToLocalCache(user: UserModel): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUserFromLocalCache(): UserModel {
    return JSON.parse(localStorage.getItem('user'));
  }

  public loadToken(): void {
    this.token = localStorage.getItem('token');
  }

  public getToken(): string {
    return this.token;
  }

  public isLoggedIn(): boolean {
    this.loadToken();
    if (this.token != null && this.token !== '') {
      if (this.jwtHeloper.decodeToken(this.token).sub != null || '') {
        if (!this.jwtHeloper.isTokenExpired(this.token)) {
          this.loggedInUsername = this.jwtHeloper.decodeToken(this.token).sub;
          return true;
        }
      }
    } else {
      this.logout();
      return false;
    }
  }
}
