import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {HomeRoutingModule} from './home-routing.module';
import {BodyComponent} from './body/body.component';
import {CartComponent} from './cart/cart.component';
import { DetailComponent } from './detail/detail.component';
import { ProfileComponent } from './profile/profile.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MapComponent } from './map/map.component';
import {AgmCoreModule} from "@agm/core";

@NgModule({
  declarations: [BodyComponent, CartComponent, DetailComponent, ProfileComponent, MapComponent],
  exports: [],
  imports: [
    CommonModule,
    HomeRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    AgmCoreModule
  ]
})
export class HomeModule {
}
