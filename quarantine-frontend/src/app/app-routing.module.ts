import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatListComponent } from './components/chat-list/chat-list.component';
import { ChatComponent } from './components/chat/chat.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { RequestsComponent } from './components/requests/requests.component';
import { UserListComponent } from './components/user-list/user-list.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'users', component: UserListComponent },
  { path: 'requests', component: RequestsComponent },
  { path: 'chats', component: ChatListComponent },
  { path: 'chat/:chatId', component: ChatComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

 }
