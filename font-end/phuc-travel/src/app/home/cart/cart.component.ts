import {Component, OnDestroy, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ToursService} from "../../service/tours/tours.service";
import {TokenService} from "../../service/user/token.service";
import {NavigationEnd, Router} from "@angular/router";
import {BookingService} from "../../service/booking/booking.service";
import {LoginService} from "../../service/user/login.service";
import {Tours} from "../../entity/tours";
import {ShareService} from "../../service/user/share.service";
import {Booking} from "../../entity/booking";
import {render} from 'creditcardpayments/creditCardPayments';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit, OnDestroy {
  tours: Tours[];
  bookings: Booking[] = [];
  total = 0;

  constructor(private toursService: ToursService,
              private token: TokenService, private router: Router
    , private bookingService: BookingService,
              private login: LoginService,
              private share: ShareService) {
    this.getTours();
    render({
      id: "#paypal",
      currency: "USD",
      value: "100.00",
      onApprove: (details) => {
        console.log(details)
        alert("Thanh toán thành công")
      }
    })
  }

  ngOnInit(): void {
    this.share.getClickEvent();
    window.scroll(0, 980)

  }


  deleteTour(id: number) {
    this.bookingService.deleteCart(id).subscribe(data => {
      this.share.sendClickEvent();
      this.getTours();
    })
  }

  changeQuantity(quantity, index) {
    this.bookings[index].slot = quantity
    this.getTotal()
  }

  getTours() {
    if (this.token.isLogger()) {
      this.bookingService.getListCart(this.token.getId()).subscribe(data => {
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
    for (let i = 0; i < this.bookings.length; i++) {
      this.total += this.bookings[i].slot * this.bookings[i].tours.price;
    }
  }
}


