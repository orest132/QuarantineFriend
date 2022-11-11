import { HttpClient } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { UserModel, RequestModel } from "../model/userModel";
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';


@Injectable({
  providedIn: 'root',
})

export class RequestService implements OnInit {
  delete(id: number) {
    return this.httpClient.delete(`http://localhost:8080/api/request/${id}`);
  }
    constructor(private httpClient: HttpClient, private router: Router) {}

  ngOnInit(): void {
    // this.userLoggedIn.next(JSON.parse(localStorage.getItem('user')));
  }

  sendRequest(request: RequestModel, id:number) {
    return this.httpClient.post(`http://localhost:8080/api/request/send/${id}`, request).subscribe;
  }

  getRequestsByUserId(id:number) {
    return this.httpClient.get<RequestModel[]>(`http://localhost:8080/api/request/all/${id}`);
  }

}