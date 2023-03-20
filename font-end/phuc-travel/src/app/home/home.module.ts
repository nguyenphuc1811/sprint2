import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {HomeRoutingModule} from './home-routing.module';
import {BodyComponent} from './body/body.component';
import {CartComponent} from './cart/cart.component';
import { DetailComponent } from './detail/detail.component';

@NgModule({
  declarations: [BodyComponent, CartComponent, DetailComponent],
  exports: [
    BodyComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule
  ]
})
export class HomeModule {
}
