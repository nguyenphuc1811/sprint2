import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Booking} from "../../entity/booking";
import {Tours} from "../../entity/tours";
import {User} from "../../entity/user";

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  URL = 'http://localhost:8080/api/booking/';

  constructor(private httpClient: HttpClient) {
  }

  getListCart(id: string): Observable<any> {
    return this.httpClient.get(this.URL + id);
  }

  getDetailCart(id: string, page :number): Observable<any> {
    return this.httpClient.get(this.URL + 'detail/' + id + '?page=' + page );
  }

  addBooking(tours: Tours, user: User): Observable<any> {
    let booking: Booking = {
      user: {
        id: user.id
      }, tours: {
        id: tours.id
      }
    }
    return this.httpClient.post(this.URL + "add", booking);
  }

  addBookingAndSlot(tours: Tours, user: User, slot: string): Observable<any> {
    let booking: Booking = {
      user: {
        id: user.id
      }, tours: {
        id: tours.id
      },
      slot: parseInt(slot)
    }
    return this.httpClient.post(this.URL + "add", booking);
  }

  deleteCart(id: number): Observable<any> {
    return this.httpClient.delete(this.URL + id);
  }

  updateCart(booking: Booking[]) {
    return this.httpClient.put(this.URL + "update", booking);
  }

  buyAll(booking: Booking[]) {
    return this.httpClient.put(this.URL + "payment", booking);
  }

  checkBooking(booking: Booking[]) {
    return this.httpClient.post(this.URL + "payment", booking);
  }
}
