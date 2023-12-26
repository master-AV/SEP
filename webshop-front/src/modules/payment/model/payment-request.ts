export interface PaymentRequest {
    userId: number;
    offerId: number;
    method: string;
    subscribedMembership: boolean;
};

export interface RedirectInfo {
    status?: string,
    redirectUrl?: string;
}