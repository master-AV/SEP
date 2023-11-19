package ftn.sep.classes;

import ftn.sep.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PSPClient {
    private int id;
    private String name;
    private List<PaymentType> supportedPayments;
}
