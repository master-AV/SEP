package ftn.sep.webshop.service.implementation;

import ftn.sep.db.Offer;
import ftn.sep.webshop.dto.response.OfferResponse;
import ftn.sep.webshop.repository.OfferRepository;
import ftn.sep.webshop.service.interfaces.IOfferService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ftn.sep.webshop.dto.response.OfferResponse.formOfferResponses;
import static ftn.sep.webshop.util.Constants.OFFER_NOT_FOUND;

@Service
public class OfferService implements IOfferService {

    @Autowired
    private OfferRepository offerRepository;
    @Override
    public List<OfferResponse> getAllOffers() {

        return formOfferResponses(offerRepository.findAll());
    }

    @Override
    public double getOfferPriceById(int id) {
        return offerRepository.findOfferPriceById(id);
    }

    public OfferResponse getById(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(OFFER_NOT_FOUND));

        return new OfferResponse(offer);
    }
}
