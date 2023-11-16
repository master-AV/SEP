package classes;

import enums.OfferType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Offer {
    private int id;
    private OfferType type;
    private int monthlyPrice;
    private int yearlyPrice;
}
