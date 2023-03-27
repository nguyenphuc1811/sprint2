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

  getListCart(id: number): Observable<any> {
    return this.httpClient.get(this.URL + id);
  }

  deleteTours(id: number): Observable<any> {
    return this.httpClient.delete(this.URL + id);
  }

  addBooking(tours: Tours, user: User): Observable<any> {
    let booking: Booking = {
      user: user, tours: tours
    }
    return this.httpClient.post(this.URL + "add", booking);
  }
}
