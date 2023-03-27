import {Tours} from "./tours";
import {User} from "./user";

export interface Booking {
  id?: number
  bookingDate?: string;
  slot?: number;
  tours?: Tours;
  user?: User;
}
