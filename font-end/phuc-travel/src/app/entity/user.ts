import {Cart} from "./cart";

export interface User {
  avatar?: string;
  id?: number;
  name?: string;
  username?: string;
  password?: string;
  phoneNumber?: string;
  email?: string;
  address?: string;
  age?: number;
  gender?: boolean;
  dateOfBirth?: string;
  cart?: Cart;
}
