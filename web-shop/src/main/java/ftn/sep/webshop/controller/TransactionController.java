package ftn.sep.webshop.controller;

import ftn.sep.dto.request.TransactionRequest;
import ftn.sep.webshop.dto.response.TransactionResponse;
import ftn.sep.webshop.exception.EntityNotFoundException;
import ftn.sep.webshop.service.interfaces.ITransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {
    @Autowired
    private ITransactionService transactionService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody TransactionRequest transactionRequest) throws EntityNotFoundException {
        transactionService.create(transactionRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getAll(){
        return transactionService.getAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getByUserId(@PathVariable @Valid @NotNull Long userId) {
        
        return transactionService.getByUserId(userId);
    }
}
