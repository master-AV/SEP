package ftn.sep.webshop.service.implementation;

import ftn.sep.db.Offer;
import ftn.sep.enums.OfferType;
import ftn.sep.webshop.dto.response.OfferResponse;
import ftn.sep.webshop.repository.OfferRepository;
import ftn.sep.webshop.repository.UserRepository;
import ftn.sep.webshop.service.interfaces.IOfferService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ftn.sep.webshop.dto.response.OfferResponse.formOfferResponses;

@Service
public class OfferService implements IOfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<OfferResponse> getAllOffers() {

        return formOfferResponses(offerRepository.findAll());
    }

    @Override
    public double getOfferPriceById(int id) {
        return offerRepository.findOfferPriceById(id);
    }

    public Offer getById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public OfferResponse getResponseById(Long id) {
        return new OfferResponse(getById(id));
    }

    @Override
    public Offer getOfferByType(OfferType type) {
        return offerRepository.findByType(type)
                .orElseThrow(EntityNotFoundException::new);
    }
}
