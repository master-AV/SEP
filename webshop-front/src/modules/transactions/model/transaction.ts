import { Offer } from "src/model/offer";
import { User } from "src/modules/auth/model/user";

export interface Transaction {
    id: number;
    paidDate: Date;
    paymentMethod: string;
    offer: Offer;
    user: User;
}