package classes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Administration {
    private int id;
    private String name;
    private AdministrationLevel level;
    private int numberOfUsers;
    private String email;
    private String password;
    private PaymentType prefferedPaymentType; // init - taj koji koristi za clanarinu pri reg
    private boolean subscribed; // if true -- automatski se obnavlja clanarina
}
