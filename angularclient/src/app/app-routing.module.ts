import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component'
import { UserListComponent } from './user-list/user-list.component';
import { UserFormComponent } from './signup/user-form.component';
import { LoginComponent } from './login/login.component';
import { UserPageComponent} from './user-page/user-page.component';
import { UserUpdateComponent } from './user-update/user-update.component';
import { UserDeleteComponent } from './user-delete/user-delete.component';
import { AddressFormComponent } from './address-form/address-form.component';
import { AddressUpdateComponent } from './address-update/address-update.component';
import { AddressDeleteComponent } from './address-delete/address-delete.component';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductCreateComponent } from './product-create/product-create.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductUpdateComponent } from './product-update/product-update.component';
import { ProductDeleteComponent } from './product-delete/product-delete.component';
import { CategoryCreateComponent } from './category-create/category-create.component';
import { CategoryPageComponent } from './category-page/category-page.component';
import { CategoryUpdateComponent } from './category-update/category-update.component';
import { CategoryDeleteComponent } from './category-delete/category-delete.component';
import { PropertyListComponent } from './property-list/property-list.component';
import { PropertyCreateComponent } from './property-create/property-create.component';
import { PropertyUpdateComponent } from './property-update/property-update.component';
import { PropertyDeleteComponent } from './property-delete/property-delete.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'users', component: UserListComponent },
  { path: 'registration', component: UserFormComponent },
  { path: 'login', component: LoginComponent },
  { path: 'users/:id', component: UserPageComponent },
  { path: 'users/:id/update', component: UserUpdateComponent },
  { path: 'users/:id/delete', component: UserDeleteComponent },
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
  { path: 'properties/:id/delete', component: PropertyDeleteComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
