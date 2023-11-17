package ftn.sep.webshop.service.interfaces;

import ftn.sep.webshop.dto.response.OfferResponse;

import java.util.List;

public interface IOfferService {
    List<OfferResponse> getAllOffers();

}
