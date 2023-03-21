import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeModule} from "./home/home.module";
import {HeaderComponent} from "./home/header/header.component";
import {FooterComponent} from "./home/footer/footer.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {TokenService} from "./service/user/token.service";
import {LoginModule} from "./login/login.module";

@NgModule({
  declarations: [
    HeaderComponent,
    AppComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HomeModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    LoginModule
  ],
  providers: [TokenService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
