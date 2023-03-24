import {TourGuide} from "./tour-guide";
import {Location} from "./location";

export interface Tours {
  cost?: number;
  description?: string;
  endDate?: string;
  name?: string;
  price?: number;
  schedule?: string;
  slot?: number;
  startDate?: string;
  tourGuide?: TourGuide;
  location?: Location;
  remaining?: number;
  img?: string;
}
