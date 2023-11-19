package ftn.sep.webshop.controller;

import ftn.sep.webshop.dto.response.OfferResponse;
import ftn.sep.webshop.service.implementation.OfferService;
import ftn.sep.webshop.service.interfaces.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("offers")
@CrossOrigin(origins = "http://localhost:4200/") //ali ovo nije resenje!!
public class OfferController {

    @Autowired
    private IOfferService offerService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<OfferResponse> getAllOffers(){

        return offerService.getAllOffers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferResponse getById(@PathVariable Long id) {

        return offerService.getById(id);
    }

}
