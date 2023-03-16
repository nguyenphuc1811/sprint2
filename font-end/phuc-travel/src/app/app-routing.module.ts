import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginRoutingModule} from "./login/login-routing.module";


const routes: Routes = [
  {path: '', loadChildren: () => import("./home/home-routing.module").then(module => module.HomeRoutingModule)},
  {path: 'login', loadChildren: () => import("./login/login-routing.module").then(module => module.LoginRoutingModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
