package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object representing a person's name.
 * @param firstName the person's first name
 * @param lastName the person's last name
 */
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
