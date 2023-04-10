import {Component, OnInit} from '@angular/core';
import {ToursService} from "../../service/tours/tours.service";
import {Tours} from "../../entity/tours";
import {Location} from "../../entity/location";
import {TokenService} from "../../service/user/token.service";
import Swal from "sweetalert2";
import {Router} from "@angular/router";
import {BookingService} from "../../service/booking/booking.service";
import {LoginService} from "../../service/user/login.service";
import {User} from "../../entity/user";
import {ShareService} from "../../service/user/share.service";

const Toast = Swal.mixin({
  toast: true,
  position: 'center-right',
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.addEventListener('mouseenter', Swal.stopTimer)
    toast.addEventListener('mouseleave', Swal.resumeTimer)
  }
})

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {
  tours: Tours[] = [];
  locations: Location[] = [];
  id = '0';
  startDate1 = '';
  slot1 = '0';
  page = 0;
  last: boolean;
  user: User;
  isLogged = false;

  constructor(private toursService: ToursService,
              private token: TokenService, private router: Router
    , private booking: BookingService,
              private login: LoginService,
              private share: ShareService) {
    this.toursService.getLocation().subscribe(data => {
      this.locations = data;
    })
    this.toursService.getTours(this.id, this.startDate1, this.slot1, this.page).subscribe(
      data => {
        this.tours = data.content;
        this.last = data.last;
      }
    )
    this.isLogged = this.token.isLogger()
    this.loader();
    this.share.getClickEvent().subscribe(
      next => {
        this.isLogged = this.token.isLogger()
        this.loader();
      }
    )

  }

  ngOnInit(): void {
  }

  loader() {
    if (this.isLogged) {
      this.login.profile(this.token.getId()).subscribe(user => {
        this.user = user;
      })
    }
  }

  searchTours(id: string, startDate: string, slot: string) {
    this.page = 0;
    this.id = id;
    this.startDate1 = startDate;
    this.slot1 = slot;
    window.scroll(0, 2000)
    this.toursService.getTours(this.id, this.startDate1, this.slot1, this.page).subscribe(
      data => {
        this.tours = data.content;
        this.last = data.last;
      }
    )
  }

  seeMore() {
    this.toursService.getTours(this.id, this.startDate1, this.slot1, ++this.page).subscribe(
      data => {
        for (let i = 0; i < data.content.length; i++) {
          this.tours.push(data.content[i]);
        }
        this.last = data.last;
      }
    )
  }

  addToCart(tour: Tours) {
    if (this.token.isLogger()) {
      this.booking.addBooking(tour, this.user).subscribe(next => {
        this.share.sendClickEvent();
        Toast.fire({
          html: '<span style="font-size: 16px;color: blue">Đã thêm vào giỏ hàng</span>  <img style="width: 300px;height: 100px;object-fit: cover"  src="' + tour.img + '">'
        })
      }, error => {
        console.log(error)
        if (error.status == 401) {
          this.router.navigateByUrl('/login')
        }
        Toast.fire({
          html: '<span style="font-size: 16px;color: red">Đã có trong giỏ hàng. Vui lòng nhập số lượng tại giỏ hàng</span>  <img style="width: 300px;height: 100px;object-fit: cover"  src="' + tour.img + '">'
        })
      })
    } else {
      Swal.fire({
        title: 'Vui lòng đăng nhập để đặt tour',
        imageUrl: 'https://cdn0.iconfinder.com/data/icons/people-137/513/gamer-512.png',
        showConfirmButton: true,
        imageWidth: 200,
        imageHeight: 200,
        imageAlt: 'Custom image',
        confirmButtonColor: '#005ec4',
        confirmButtonText: 'Đăng nhập',
      }).then((result) => {
        if (result.isConfirmed) {
          this.router.navigate(['/login'])
        }
      })
    }
  }
}
