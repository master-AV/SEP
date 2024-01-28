package ftn.sep.webshop.dto.response;

import ftn.sep.db.Role;
import ftn.sep.db.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private LocalDateTime expiresMembership;
    private boolean yearlySubscription;
    private String paymentMethod;

    public UserResponse(Long id, String email, String password, String name, String surname, Role role, LocalDateTime expiresMembership, boolean yearlySubscription, String paymentMethod) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.expiresMembership = expiresMembership;
        this.yearlySubscription = yearlySubscription;
        this.paymentMethod = paymentMethod;
    }

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRole();
        this.expiresMembership = user.getExpiresMembership();
        this.yearlySubscription = user.isYearlySubscription();
        this.paymentMethod = user.getPaymentMethod();
    }

    public static List<UserResponse> formUserResponses(List<User> users) {
        List<UserResponse> userResponses = new LinkedList<>();
        users.forEach(user ->
                userResponses.add(new UserResponse(user))
        );

        return userResponses;
    }
}
