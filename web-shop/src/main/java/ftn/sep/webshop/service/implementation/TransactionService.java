package ftn.sep.webshop.service.implementation;

import ftn.sep.db.Offer;
import ftn.sep.db.Transaction;
import ftn.sep.db.User;
import ftn.sep.dto.request.TransactionRequest;
import ftn.sep.webshop.dto.response.TransactionResponse;
import ftn.sep.webshop.exception.EntityNotFoundException;
import ftn.sep.webshop.repository.TransactionRepository;
import ftn.sep.webshop.service.interfaces.IOfferService;
import ftn.sep.webshop.service.interfaces.ITransactionService;
import ftn.sep.webshop.service.interfaces.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static ftn.sep.webshop.dto.response.TransactionResponse.formTransactionResponses;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private IOfferService offerService;
    @Autowired
    private IUsersService userService;
    @Override
    public void create(TransactionRequest transactionRequest) throws EntityNotFoundException {
        User user = userService.getById(transactionRequest.getUserId());
        Offer offer = offerService.getById(transactionRequest.getOfferId());
        Transaction transaction = new Transaction(transactionRequest.getPaidDate(), offer, transactionRequest.getPaymentMethod(), user);
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionResponse> getAll() {

        return formTransactionResponses(transactionRepository.findAllOrderByPaidDate());
    }

    @Override
    public List<TransactionResponse> getByUserId(Long userId) {

        return formTransactionResponses(transactionRepository.findAllByUserIdOrderByPaidDate(userId));
    }


}
