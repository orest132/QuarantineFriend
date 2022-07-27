import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatListComponent } from './components/chat-list/chat-list.component';
import { ChatComponent } from './components/chat/chat.component';
import { EditComponent } from './components/edit/edit.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ReportListComponent } from './components/report-list/report-list.component';
import { ReportComponent } from './components/report/report.component';
import { RequestsComponent } from './components/requests/requests.component';
import { UserListComponent } from './components/user-list/user-list.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'users', component: UserListComponent },
  { path: 'requests', component: RequestsComponent },
  { path: 'chats', component: ChatListComponent },
  { path: 'chat/:chatId', component: ChatComponent },
  { path: 'profile/:username', component: EditComponent },
  { path: 'report/:chatId', component: ReportComponent },
  { path: 'reports', component: ReportListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

 }
