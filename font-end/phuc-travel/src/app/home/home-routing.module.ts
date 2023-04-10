import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BodyComponent} from "./body/body.component";
import {CartComponent} from "./cart/cart.component";
import {DetailComponent} from "./detail/detail.component";
import {ProfileComponent} from "./profile/profile.component";
import {EmployeeGuard} from "../service/sercurity/employee.guard";
import {MapComponent} from "./map/map.component";


const routes: Routes = [
  {path: '', component: BodyComponent},
  {path: 'cart', component: CartComponent},
  {path: 'detail/:id', component: DetailComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'map', component: MapComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule {
}
