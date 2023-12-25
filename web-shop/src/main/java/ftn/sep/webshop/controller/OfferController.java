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
public class OfferController {

    @Autowired
    private IOfferService offerService;

    @GetMapping(value = "/hi")
    public void hiFunc() {
        System.out.println("HEEEEY FROM API gateway");
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<OfferResponse> getAllOffers(){

        return offerService.getAllOffers();
    }

    @GetMapping(value = "/price/{id}")
    @ResponseStatus(HttpStatus.OK)
    public double getOfferPriceById(@PathVariable int id){
        System.out.println(offerService.getOfferPriceById(id));
        return offerService.getOfferPriceById(id);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferResponse getById(@PathVariable Long id) {

        return offerService.getById(id);
    }

}
