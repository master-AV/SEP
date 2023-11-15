package classes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Transaction {
    private LocalDateTime paid;
    private boolean yearly;// if yearly - true if monthly - false
    private List<Offer> offers;
    private PaymentType paymentType;
}
