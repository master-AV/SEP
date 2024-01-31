package ftn.sep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDTO {
    private Long userId;
    private boolean subscription;
    private String paymentMethod;
}
