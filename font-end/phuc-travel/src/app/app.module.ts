import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeModule} from "./home/home.module";
import {HeaderComponent} from "./home/header/header.component";
import {FooterComponent} from "./home/footer/footer.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {TokenService} from "./service/user/token.service";
import {LoginModule} from "./login/login.module";
import {AngularFireModule} from "@angular/fire";
import {environment} from "../environments/environment";
import {AuthInterceptor} from "./service/sercurity/auth.interceptor";
import {AgmCoreModule} from "@agm/core";
import {AngularFireStorageModule} from "@angular/fire/storage";

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
    LoginModule,
    AngularFireStorageModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AgmCoreModule.forRoot({
      apiKey: "AIzaSyBHquxV3Kc335AGptol5ab7k8lagVCSqPU",
      libraries: ["places", "geometry"]
    })
  ],
  providers: [TokenService,
  ,{
    provide:HTTP_INTERCEPTORS,
      useClass:AuthInterceptor,
      multi:true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
