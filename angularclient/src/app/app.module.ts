import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { MatRadioModule } from '@angular/material/radio';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { DatePipe } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BasicAuthHttpInterceptorService } from './service/basic-auth-http-interceptor.service';

import { HomeComponent } from './view/home/home.component';

import { UserService } from './service/user.service';
import { UserListComponent } from './view/user/user-list/user-list.component';
import { UserFormComponent } from './view/user/signup/user-form.component';
import { UserPageComponent } from './view/user/user-page/user-page.component';
import { UserUpdateComponent } from './view/user/user-update/user-update.component';
import { UserDeleteComponent } from './view/user/user-delete/user-delete.component';
import { LoginComponent } from './view/user/login/login.component';

import { AddressService } from './service/address.service';
import { AddressFormComponent } from './view/address/address-form/address-form.component';
import { AddressDeleteComponent } from './view/address/address-delete/address-delete.component';
import { AddressUpdateComponent } from './view/address/address-update/address-update.component';

import { ProductListComponent } from './view/product/product-list/product-list.component';
import { ProductCreateComponent } from './view/product/product-create/product-create.component';
import { ProductDetailComponent } from './view/product/product-detail/product-detail.component';
import { ProductUpdateComponent } from './view/product/product-update/product-update.component';
import { ProductDeleteComponent } from './view/product/product-delete/product-delete.component';

import { CategoryPageComponent } from './view/category/category-page/category-page.component';
import { CategoryCreateComponent } from './view/category/category-create/category-create.component';
import { CategoryUpdateComponent } from './view/category/category-update/category-update.component';
import { CategoryDeleteComponent } from './view/category/category-delete/category-delete.component';

import { PropertyListComponent } from './view/property/property-list/property-list.component';
import { PropertyCreateComponent } from './view/property/property-create/property-create.component';
import { PropertyUpdateComponent } from './view/property/property-update/property-update.component';
import { PropertyDeleteComponent } from './view/property/property-delete/property-delete.component';
import { PropertyValueListComponent } from './view/property-value/property-value-list/property-value-list.component';
import { PropertyValueCreateComponent } from './view/property-value/property-value-create/property-value-create.component';
import { PropertyValueUpdateComponent } from './view/property-value/property-value-update/property-value-update.component';
import { PropertyValueDeleteComponent } from './view/property-value/property-value-delete/property-value-delete.component';

import { OrderFormComponent } from './view/order/order-form/order-form.component';
import { OrderUserListComponent } from './view/order/order-user-list/order-user-list.component';
import { OrderUpdateComponent } from './view/order/order-update/order-update.component';

import { UnauthorizedComponent } from './util/unauthorized/unauthorized.component';
import { UnauthorizedInterceptor } from './util/unauthorized.interceptor';
import { UserChangePasswordComponent } from './view/user/user-change-password/user-change-password.component';
import { AddressAdminListComponent } from './view/address/address-admin-list/address-admin-list.component';
import { UserChangeRoleComponent } from './view/user/user-change-role/user-change-role.component';
import { OrderAdminListComponent } from './view/order/order-admin-list/order-admin-list.component';
import { RevenueComponent } from './view/statistics/revenue/revenue.component';
import { TopProductsComponent } from './view/statistics/top-products/top-products.component';
import { TopBuyersComponent } from './view/statistics/top-buyers/top-buyers.component';

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
    UserUpdateComponent,
    ProductDetailComponent,
    ProductCreateComponent,
    ProductUpdateComponent,
    ProductDeleteComponent,
    CategoryPageComponent,
    CategoryCreateComponent,
    CategoryUpdateComponent,
    CategoryDeleteComponent,
    PropertyListComponent,
    PropertyCreateComponent,
    PropertyUpdateComponent,
    PropertyDeleteComponent,
    PropertyValueListComponent,
    PropertyValueCreateComponent,
    PropertyValueUpdateComponent,
    PropertyValueDeleteComponent,
    OrderFormComponent,
    OrderUserListComponent,
    OrderUpdateComponent,
    UnauthorizedComponent,
    UserChangePasswordComponent,
    AddressAdminListComponent,
    UserChangeRoleComponent,
    OrderAdminListComponent,
    RevenueComponent,
    TopProductsComponent,
    TopBuyersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatTableModule,
    NgxChartsModule
  ],
  providers: [
    UserService,
    DatePipe,
    CookieService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: BasicAuthHttpInterceptorService,
      multi: true
    },
    { provide: HTTP_INTERCEPTORS,
      useClass: UnauthorizedInterceptor,
      multi: true
    },
      ],
  bootstrap: [AppComponent]
})
export class AppModule { }
