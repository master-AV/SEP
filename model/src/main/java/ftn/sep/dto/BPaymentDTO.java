package ftn.sep.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BPaymentDTO extends PaymentDTO{
    private String receivingAccountID;
    private String receivingAccountKey;
    private String sendingAccountID;
    private String sendingAccountKey;

    public BPaymentDTO(PaymentDTO paymentDTO, String receiverAccId, String receiverAccKey, String sendingAccountID, String sendingAccountKey){
        this.price = paymentDTO.getPrice();
        this.userId = paymentDTO.getUserId();
        this.receivingAccountID = receiverAccId;
        this.sendingAccountID = sendingAccountID;
        this.sendingAccountKey = sendingAccountKey;
        this.receivingAccountKey = receiverAccKey;
    }
}
