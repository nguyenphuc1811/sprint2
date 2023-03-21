import {Tours} from "./tours";
import {User} from "./user";

export interface Bookings {
  bookingDate?: string;
  slot?: number;
  tours?: Tours;
  user?: User;
}
