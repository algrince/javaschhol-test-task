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
  { path: 'products/:id', component: ProductDetailComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
