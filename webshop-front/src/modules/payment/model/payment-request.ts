export interface PaymentRequest {
    userId: number;
    offerId: number;
};

export interface RedirectInfo {
    status?: string,
    redirectUrl?: string;
}