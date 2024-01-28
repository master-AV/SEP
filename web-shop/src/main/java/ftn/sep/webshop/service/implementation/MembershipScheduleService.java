package ftn.sep.webshop.service.implementation;

import ftn.sep.db.Offer;
import ftn.sep.dto.request.PaymentRequest;
import ftn.sep.enums.OfferType;
import ftn.sep.webshop.dto.response.UserResponse;
import ftn.sep.webshop.service.interfaces.IMembershipScheduleService;
import ftn.sep.webshop.service.interfaces.IOfferService;
import ftn.sep.webshop.service.interfaces.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service
public class MembershipScheduleService implements IMembershipScheduleService {
    @Autowired
    private IUsersService userService;
    @Autowired
    private IOfferService offerService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${apigateway.url}")
    private String apigatewayUrl;

    @Override
    public void renewMembership() {
        List<UserResponse> usersWithExpiredMembership = userService.getUsersWithExpiredMembership();
        Offer offer = offerService.getOfferByType(OfferType.MEMBERSHIP);
        for(UserResponse user : usersWithExpiredMembership) {
            try {
                URL url = new URL(apigatewayUrl + "/users/membership");
                PaymentRequest request = new PaymentRequest(user.getId(), offer.getId(), user.getPaymentMethod(), true);
                restTemplate.postForEntity(url.toURI(), request, Object.class);
            } catch (MalformedURLException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
