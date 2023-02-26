import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { UserListComponent } from './user-list/user-list.component';
import { UserService } from './service/user.service'

import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { UserFormComponent } from './signup/user-form.component';
import { LoginComponent } from './login/login.component';
import { UserPageComponent } from './user-page/user-page.component';
import { AddressFormComponent } from './address-form/address-form.component';
import { UserDeleteComponent } from './user-delete/user-delete.component';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    HomeComponent,
    UserFormComponent,
    LoginComponent,
    UserPageComponent,
    AddressFormComponent,
    UserDeleteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
