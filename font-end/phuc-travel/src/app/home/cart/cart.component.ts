import {Component, OnDestroy, OnInit} from '@angular/core';
import {ToursService} from "../../service/tours/tours.service";
import {TokenService} from "../../service/user/token.service";
import {NavigationEnd, Router} from "@angular/router";
import {BookingService} from "../../service/booking/booking.service";
import {LoginService} from "../../service/user/login.service";
import {Tours} from "../../entity/tours";
import {ShareService} from "../../service/user/share.service";
import {Booking} from "../../entity/booking";
import {render} from 'creditcardpayments/creditCardPayments';
import Swal from "sweetalert2";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit, OnDestroy {
  tours: Tours[];
  bookings: Booking[] = [];
  total = 0;
  checkShow = false;

  constructor(private toursService: ToursService,
              private token: TokenService, private router: Router
    , private bookingService: BookingService,
              private login: LoginService,
              private share: ShareService) {
    this.getTours();
  }

  showRender() {
    this.checkShow = true
    let money = +(this.total / 23485.48).toFixed(2);
    render({
      id: "#paypal",
      currency: "USD",
      value: String(money),
      onApprove: (details) => {
        this.bookingService.buyAll(this.bookings).subscribe(data => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Bạn đã đặt thành công, tổng số tiền là: ' + this.total + 'đ',
            showConfirmButton: false,
            timer: 1500
          })
          this.share.sendClickEvent();
          this.total = 0;
          this.bookings = [];
        });
      }
    })
  }

  ngOnInit(): void {
    this.share.getClickEvent();
    window.scroll(0, 1000)

  }


  deleteTour(id: number) {
    this.bookingService.deleteCart(id).subscribe(data => {
      this.getTours();
      this.share.sendClickEvent();
    })
  }

  changeQuantity(quantity, index) {
    this.bookings[index].slot = quantity
    this.getTotal()
  }

  getTours() {
    if (this.token.isLogger()) {
      this.bookingService.getListCart(this.token.getId()).subscribe(data => {
        this.bookings = [];
        for (let i = 0; i < data.length; i++) {
          this.bookings.push({
            id: data[i].id,
            slot: data[i].slot,
            tours: {
              id: data[i].id,
              schedule: data[i].iToursDto.schedule,
              startDate: data[i].iToursDto.startDate,
              endDate: data[i].iToursDto.endDate,
              img: data[i].iToursDto.img,
              description: data[i].iToursDto.description,
              remaining: data[i].iToursDto.remaining,
              price: data[i].iToursDto.price,
              location: {
                id: data[i].iToursDto.locationId,
                name: data[i].iToursDto.location
              }
            }
          })
        }
        this.getTotal()
      })
    }
  }

  ngOnDestroy(): void {
    this.bookingService.updateCart(this.bookings).subscribe();
  }

  getTotal() {
    this.total = 0;
    for (let i = 0; i < this.bookings.length; i++) {
      this.total += this.bookings[i].slot * this.bookings[i].tours.price;
    }
  }
}


