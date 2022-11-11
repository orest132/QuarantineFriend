import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message } from '../model/userModel';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private rootUlr: string = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {}


  getMessagesByChatId(id: number){
    return this.httpClient.get<Message[]>(
        `${this.rootUlr}/api/get/messages/chat/${id}`
    );
  }

  postMessageToChat(id:number, message: Message){
    return this.httpClient.post(`${this.rootUlr}/api/message/chat/${id}`, message);
  }

//   addBoard(board: Board) {
//     return this.httpClient.post(`${this.rootUlr}/api/add-board`, board);
//   }

//   getBoardsById(id: number): Observable<Board[]> {
//     return this.httpClient.get<Board[]>(
//       `${this.rootUlr}/api/boards/user/${id}`
//     );
//   }
//   getBoardById(id: number): Observable<Board> {
//     return this.httpClient.get<Board>(`${this.rootUlr}/api/boards/${id}`);
//   }

//   deleteBoardById(id: number) {
//     return this.httpClient.delete(`${this.rootUlr}/api/boards/${id}`);
//   }
}