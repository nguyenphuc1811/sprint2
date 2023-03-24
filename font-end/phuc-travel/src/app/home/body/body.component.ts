import {Component, OnInit} from '@angular/core';
import {ToursService} from "../../service/tours/tours.service";
import {Tours} from "../../entity/tours";

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})
export class BodyComponent implements OnInit {
  tours: Tours[] = [];
  id = 0;
  startDate = '';
  slot = 0;
  page = 0;
  last: boolean;

  constructor(private toursService: ToursService) {
    this.searchTours(this.id, this.startDate, this.slot);
  }

  ngOnInit(): void {
  }

  searchTours(id: number, startDate: string, slot: number) {
    this.id = id;
    this.startDate = startDate;
    this.slot = slot;
    this.page = 0;
    this.toursService.getTours(this.id, this.startDate, this.slot, this.page).subscribe(
      data => {
        for (let i = 0; i < data.content.length; i++) {
          this.tours.push(data.content[i]);
        }
        console.log(this.tours)
        this.last = data.last;
      }
    )
  }

  seeMore() {
    this.toursService.getTours(this.id, this.startDate, this.slot, ++this.page).subscribe(
      data => {
        this.tours.push(data.content);
        this.last = data.last;
      }
    )
  }
}
