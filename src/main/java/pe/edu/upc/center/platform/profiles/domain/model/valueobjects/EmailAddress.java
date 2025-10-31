package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable
public record EmailAddress(@Email @NotNull @NotBlank String address) {

    public EmailAddress {
        if (Objects.isNull(address) || address.isBlank()) {
            throw new IllegalArgumentException("Email address cannot be null or blank");
        }
        if (!address.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    public EmailAddress() {
        this(null);
    }
}
