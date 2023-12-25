export interface PaymentMethod {
    id: number;
    name: string;
    img: string;
    subscribed: boolean;
};

export interface UpdatePaymentMethod {
    name: string;
    subscribed: boolean;
}