import { MatSliderModule } from '@angular/material/slider';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './components/login/login.component';
import { MenuComponent } from './components/menu/menu.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatFormFieldControl } from '@angular/material/form-field';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatStepperModule } from '@angular/material/stepper';
import { MatRadioModule } from '@angular/material/radio';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTreeModule } from '@angular/material/tree';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { MatChipsModule } from '@angular/material/chips';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatBadgeModule } from '@angular/material/badge';

import {
  AlertComponent,
  PasswordChanged,
  TeamAdded,
} from './components/alert/alert.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { RegisterComponent } from './components/register/register.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { RequestsComponent } from './components/requests/requests.component';
import { ChatListComponent } from './components/chat-list/chat-list.component';
import { ChatComponent } from './components/chat/chat.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MenuComponent,
    AlertComponent,
    RegisterComponent,
    UserListComponent,
    RequestsComponent,
    ChatListComponent,
    ChatComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatSliderModule,
    MatMenuModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatBadgeModule,
    MatMenuModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTabsModule,
    MatSidenavModule,
    MatListModule,
    MatToolbarModule,
    MatTooltipModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatStepperModule,
    MatDialogModule,
    AppRoutingModule,
    MatSelectModule,
    MatCheckboxModule,
    DragDropModule,
    MatRadioModule,
    MatSliderModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatGridListModule,
    MatTreeModule,
    ScrollingModule,
    MatChipsModule,
    MatExpansionModule,
    MatProgressBarModule,
    MatBadgeModule,
    MatIconModule,
  ],
  providers: [
    MatSnackBar,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
