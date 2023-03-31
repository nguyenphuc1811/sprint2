import {Component, OnInit} from '@angular/core';
import {User} from "../../entity/user";
import {LoginService} from "../../service/user/login.service";
import {TokenService} from "../../service/user/token.service";
import {Router} from "@angular/router";
import {ShareService} from "../../service/user/share.service";
import {Tours} from "../../entity/tours";
import {BookingService} from "../../service/booking/booking.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  user: User = {
    avatar: ''
  };
  role = 'none';
  name = 'Đăng nhập'
  isLogged = false;
  tours: Tours[] = [];

  constructor(private login: LoginService, private bookingService: BookingService
    , private token: TokenService, private router: Router
    , private share: ShareService) {
  }

  ngOnInit(): void {
    this.loader();
    this.share.getClickEvent().subscribe(() => {
      this.loader();
      this.getTours();
    })
  }

  loader() {
    this.isLogged = this.token.isLogger()
    if (this.isLogged) {
      this.login.profile(this.token.getId()).subscribe(next => {
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

  getTours() {
    if (this.token.isLogger()) {
      this.bookingService.getListCart(this.token.getId()).subscribe(data => {
        this.tours = data;
        console.log(this.tours.length);
      })
    }
  }
}
