import {Component, OnInit} from '@angular/core';
import {Tours} from "../../entity/tours";
import {ToursService} from "../../service/tours/tours.service";
import {ActivatedRoute, Router} from "@angular/router";
import Swal from "sweetalert2";
import {TokenService} from "../../service/user/token.service";
import {BookingService} from "../../service/booking/booking.service";
import {LoginService} from "../../service/user/login.service";
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
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {
  tour: Tours;

  constructor(private toursService: ToursService,
              private token: TokenService, private router: Router
    , private booking: BookingService,
              private login: LoginService, private activatedRoute: ActivatedRoute,
              private share: ShareService) {
    this.activatedRoute.paramMap.subscribe(data => {
      const id = data.get('id');
      this.toursService.getDetail(parseInt(id)).subscribe(tour => {
        console.log(tour);
        this.tour = tour;
      }, error => {
      })
    })

  }

  ngOnInit(): void {
    window.scroll(0, 980)
  }

  addToCart(tour: Tours, slot: string) {
    if (this.token.isLogger()) {
      this.login.profile(this.token.getId()).subscribe(user => {
        this.booking.addBookingAndSlot(tour, user, slot).subscribe(next => {
          this.share.sendClickEvent();
          Toast.fire({
            html: '<span style="font-size: 16px;color: blue">Đã thêm vào giỏ hàng</span>  <img style="width: 300px;height: 100px;object-fit: cover"  src="' + tour.img + '">'
          })
        }, error => {
          Toast.fire({
            html: '<span style="font-size: 16px;color: red">Đã có trong giỏ hàng. Vui lòng nhập số lượng tại giỏ hàng</span>  <img style="width: 300px;height: 100px;object-fit: cover"  src="' + tour.img + '">'
          })
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
