package ftn.sep.webshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerifyMailDTO {

    private int securityCode;

    private String hashId;

    private String email;

    public VerifyMailDTO(int securityCode, String hashId, String email) {
        this.securityCode = securityCode;
        this.hashId = hashId;
        this.email = email;
    }
}
