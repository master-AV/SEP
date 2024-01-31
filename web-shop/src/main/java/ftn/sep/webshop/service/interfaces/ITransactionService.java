package ftn.sep.webshop.service.interfaces;

import ftn.sep.dto.request.TransactionRequest;
import ftn.sep.webshop.dto.response.TransactionResponse;
import ftn.sep.webshop.exception.EntityNotFoundException;

import java.util.List;

public interface ITransactionService {

    void create(TransactionRequest transactionRequest) throws EntityNotFoundException;

    List<TransactionResponse> getAll();

    List<TransactionResponse> getByUserId(Long userId);
}
