package ftn.sep.dto.response;

import ftn.sep.db.Offer;
import ftn.sep.enums.OfferType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferResponse {
    private Long id;
    private OfferType type;
    private double monthlyPrice;
    private double yearlyPrice;

    public OfferResponse(Offer offer) {
        this.id = offer.getId();
        this.type = offer.getType();
        this.monthlyPrice = offer.getMonthlyPrice();
        this.yearlyPrice = offer.getYearlyPrice();
    }
}
