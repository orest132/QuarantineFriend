import { HttpClient } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ChatModel } from "../model/userModel";


@Injectable({
  providedIn: 'root',
})

export class ChatService implements OnInit {
    constructor(private httpClient: HttpClient, private router: Router) {}

  ngOnInit(): void {
    // this.userLoggedIn.next(JSON.parse(localStorage.getItem('user')));
  }

  getChatByUserId(id:number) {
    return this.httpClient.get<ChatModel[]>(`http://localhost:8080/api/chat/user/${id}`);
  }

  getChatById(id:number) {
    return this.httpClient.get<ChatModel>(`http://localhost:8080/api/chat/${id}`);
  }

  makeChatInactiveByUser(chat:ChatModel, id:number){
    return this.httpClient.put(`http://localhost:8080/api/chat/block/${id}`, chat);
  }
}