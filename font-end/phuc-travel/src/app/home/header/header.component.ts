import { Component, OnInit } from '@angular/core';
import {User} from "../../entity/user";
import {LoginService} from "../../service/user/login.service";
import {TokenService} from "../../service/user/token.service";
import {Router} from "@angular/router";
import {ShareService} from "../../service/user/share.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  user: User;
  role = 'none';
  name = 'Đăng nhập'
  isLogged = false;

  constructor(private login: LoginService, private token: TokenService, private router: Router, private share: ShareService) {
  }

  ngOnInit(): void {
    this.loader();
    this.share.getClickEvent().subscribe(() => {
      this.loader();
    })
  }

  loader() {
    this.isLogged = this.token.isLogger()
    if (this.isLogged) {
      this.login.profile(this.token.getUsername()).subscribe(next => {
        this.user = next;
        this.name = this.user.name;
      })
      this.role = this.token.getRole();
    }
  }

  logout() {
    this.role = 'none';
    this.name = 'Đăng nhập';
    this.isLogged = false;
    this.token.logout();
    this.router.navigateByUrl('/');
  }


  checkProfile() {
    if (!this.isLogged) {
      this.router.navigateByUrl('/login')
    } else {
      this.router.navigateByUrl('/profile')
    }
  }

  search1(value: string) {
    this.share.sendClickEvent()
    this.router.navigate(['home', value])
  }

}
