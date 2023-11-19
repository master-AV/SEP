package ftn.sep.webshop.service.implementation;

import ftn.sep.webshop.dto.response.OfferResponse;
import ftn.sep.webshop.repository.OfferRepository;
import ftn.sep.webshop.service.interfaces.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ftn.sep.webshop.dto.response.OfferResponse.formOfferResponses;

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
}
