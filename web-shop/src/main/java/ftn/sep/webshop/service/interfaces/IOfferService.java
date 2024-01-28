package ftn.sep.webshop.service.interfaces;

import ftn.sep.db.Offer;
import ftn.sep.enums.OfferType;
import ftn.sep.webshop.dto.response.OfferResponse;

import java.util.List;

public interface IOfferService {
    List<OfferResponse> getAllOffers();
    double getOfferPriceById(int id);
    Offer getById(Long id);
    OfferResponse getResponseById(Long id);
    Offer getOfferByType(OfferType type);

}
