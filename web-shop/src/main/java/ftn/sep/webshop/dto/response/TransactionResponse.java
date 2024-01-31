package ftn.sep.webshop.dto.response;

import ftn.sep.db.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private LocalDateTime paidDate;
    private OfferResponse offerResponse;
    private String paymentMethod;
    private UserResponse userResponse;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.paidDate = transaction.getPaidDate();
        this.offerResponse = new OfferResponse(transaction.getOffer());
        this.paymentMethod = transaction.getPaymentMethod();
        this.userResponse = new UserResponse(transaction.getUser());
    }

    public static List<TransactionResponse> formTransactionResponses(List<Transaction> transactions) {
        List<TransactionResponse> transactionResponses = new LinkedList<>();
        transactions.forEach(user ->
                transactionResponses.add(new TransactionResponse(user))
        );

        return transactionResponses;
    }
}
