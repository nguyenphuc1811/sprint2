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

  addBooking(tours: Tours, user: User): Observable<any> {
    let booking: Booking = {
      user: {
        id: user.id
      }, tours: {
        id: tours.id
      }, bookingDate: '2023-11-11'
    }
    return this.httpClient.post(this.URL + "add", booking);
  }

  deleteCart(id: number): Observable<any> {
    return this.httpClient.delete(this.URL + id);
  }

  updateCart(booking: Booking[]) {
    return this.httpClient.put(this.URL + "update", booking);
  }
}
