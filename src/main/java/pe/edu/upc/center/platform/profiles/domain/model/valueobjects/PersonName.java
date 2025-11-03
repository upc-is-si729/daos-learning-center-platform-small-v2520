package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable
public record PersonName(String firstName, String lastName) {

    public PersonName {
        if (Objects.isNull(firstName) || firstName.isBlank()) {
            throw new IllegalArgumentException("[PersonName] First name cannot be null or blank");
        }
        if (Objects.isNull(lastName) || lastName.isBlank()) {
            throw new IllegalArgumentException("[PersonName] Last name cannot be null or blank");
        }
    }

    public PersonName() {
        this(null, null);
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
