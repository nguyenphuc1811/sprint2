import {Component, OnInit} from '@angular/core';
import {Tours} from "../../entity/tours";
import {ToursService} from "../../service/tours/tours.service";
import {ActivatedRoute, Router} from "@angular/router";
import Swal from "sweetalert2";
import {TokenService} from "../../service/user/token.service";
import {BookingService} from "../../service/booking/booking.service";
import {LoginService} from "../../service/user/login.service";

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
              private login: LoginService, private activatedRoute: ActivatedRoute) {
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

  addToCart(tour: Tours) {
    if (this.token.isLogger()) {
      this.login.profile(this.token.getId()).subscribe(user => {
        this.booking.addBooking(tour, user);
        Toast.fire({
          iconHtml: '<img style="width: 90px;height: 90px;object-fit: cover;padding: 10px"  src="' + tour.img + '">',
          title: 'Bạn đã thêm ' + tour.name + ' vào giỏ!'
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
