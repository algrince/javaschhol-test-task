import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { UserListComponent } from './user-list/user-list.component';
import { UserService } from './service/user.service';
import { AddressService } from './service/address.service';

import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';

import { UserFormComponent } from './signup/user-form.component';
import { LoginComponent } from './login/login.component';
import { UserPageComponent } from './user-page/user-page.component';
import { AddressFormComponent } from './address-form/address-form.component';
import { UserDeleteComponent } from './user-delete/user-delete.component';
import { ProductListComponent } from './product-list/product-list.component';
import { AddressDeleteComponent } from './address-delete/address-delete.component';
import { AddressUpdateComponent } from './address-update/address-update.component';
import { UserUpdateComponent } from './user-update/user-update.component';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    HomeComponent,
    UserFormComponent,
    LoginComponent,
    UserPageComponent,
    AddressFormComponent,
    UserDeleteComponent,
    ProductListComponent,
    AddressDeleteComponent,
    AddressUpdateComponent,
    UserUpdateComponent
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
