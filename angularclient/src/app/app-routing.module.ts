import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './view/home/home.component'

import { UserListComponent } from './view/user/user-list/user-list.component';
import { UserFormComponent } from './view/user/signup/user-form.component';
import { UserPageComponent} from './view/user/user-page/user-page.component';
import { UserUpdateComponent } from './view/user/user-update/user-update.component';
import { UserDeleteComponent } from './view/user/user-delete/user-delete.component';
import { LoginComponent } from './view/user/login/login.component';
import { UserChangePasswordComponent } from './view/user/user-change-password/user-change-password.component';

import { AddressFormComponent } from './view/address/address-form/address-form.component';
import { AddressUpdateComponent } from './view/address/address-update/address-update.component';
import { AddressDeleteComponent } from './view/address/address-delete/address-delete.component';

import { ProductListComponent } from './view/product/product-list/product-list.component';
import { ProductCreateComponent } from './view/product/product-create/product-create.component';
import { ProductDetailComponent } from './view/product/product-detail/product-detail.component';
import { ProductUpdateComponent } from './view/product/product-update/product-update.component';
import { ProductDeleteComponent } from './view/product/product-delete/product-delete.component';

import { CategoryCreateComponent } from './view/category/category-create/category-create.component';
import { CategoryPageComponent } from './view/category/category-page/category-page.component';
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
import { OrderUserListComponent } from './view/order/order-user-list/order-user-list.component'
import { OrderUpdateComponent } from './view/order/order-update/order-update.component';

import { UnauthorizedComponent } from './util/unauthorized/unauthorized.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'users', component: UserListComponent },
  { path: 'registration', component: UserFormComponent },
  { path: 'login', component: LoginComponent },
  { path: 'users/:id', component: UserPageComponent },
  { path: 'users/:id/update', component: UserUpdateComponent },
  { path: 'users/:id/delete', component: UserDeleteComponent },
  { path: 'users/:id/changePassword', component: UserChangePasswordComponent },
  { path: 'addresses/new', component: AddressFormComponent },
  { path: 'addresses/:id/update', component: AddressUpdateComponent },
  { path: 'addresses/:id/delete', component: AddressDeleteComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'products/new', component: ProductCreateComponent },
  { path: 'products/:id', component: ProductDetailComponent },
  { path: 'products/:id/update', component: ProductUpdateComponent },
  { path: 'products/:id/delete', component: ProductDeleteComponent },
  { path: 'category/new', component: CategoryCreateComponent },
  { path: 'category/:id', component: CategoryPageComponent },
  { path: 'category/:id/update', component: CategoryUpdateComponent },
  { path: 'category/:id/delete', component: CategoryDeleteComponent },
  { path: 'properties', component: PropertyListComponent },
  { path: 'properties/new', component: PropertyCreateComponent },
  { path: 'properties/:id/update', component: PropertyUpdateComponent },
  { path: 'properties/:id/delete', component: PropertyDeleteComponent },
  { path: 'propertyValues', component: PropertyValueListComponent },
  { path: 'propertyValues/new', component: PropertyValueCreateComponent },
  { path: 'propertyValues/:id/update', component: PropertyValueUpdateComponent },
  { path: 'propertyValues/:id/delete', component: PropertyValueDeleteComponent },
  { path: 'orders/new', component: OrderFormComponent },
  { path: 'orders', component: OrderUserListComponent },
  { path: 'orders/:id/update', component: OrderUpdateComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
