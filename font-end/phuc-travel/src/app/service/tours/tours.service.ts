import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ToursService {
  URL = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient) {
  }

  getTours(id: number, startDate: string, slot: number, page: number): Observable<any> {
    return this.httpClient.get(this.URL + 'toursDto?locationId=' + id + '&startDate=' + startDate
      + '&slot=' + slot + '&page=' + page);
  }
}
