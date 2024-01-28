package ftn.sep.webshop.dto.response;

import ftn.sep.db.Offer;
import ftn.sep.enums.OfferType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferResponse {
    private Long id;
    private OfferType type;
    private double price;

    public OfferResponse(Offer offer) {
        this.id = offer.getId();
        this.type = offer.getType();
        this.price = offer.getPrice();
    }

    public static List<OfferResponse> formOfferResponses(List<Offer> offers) {
        List<OfferResponse> offerResponses = new LinkedList<>();
        offers.forEach(offer ->
                offerResponses.add(new OfferResponse(offer))
        );

        return offerResponses;
    }
}
