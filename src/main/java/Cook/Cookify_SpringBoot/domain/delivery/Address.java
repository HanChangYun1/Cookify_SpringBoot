package Cook.Cookify_SpringBoot.domain.delivery;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    private String street;
    private String postalCode;

    private String detailAddress;

    public Address(String street, String  postalCode, String detailAddress) {
        this.street = street;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
    }
}

