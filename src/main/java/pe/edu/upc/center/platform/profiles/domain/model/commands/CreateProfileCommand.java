package pe.edu.upc.center.platform.profiles.domain.model.commands;

import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Command to create a new profile.
 *
 * @param name The name of the profile.
 * @param document The document of the profile.
 * @param birthDate The birthdate of the profile.
 * @param age The age of the profile.
 * @param email The email of the profile.
 * @param address The address of the profile.
 */
public record CreateProfileCommand(PersonName name, Document document,
                                   LocalDate birthDate, int age,
                                   EmailAddress email, StreetAddress address) {

  public CreateProfileCommand {
    Objects.requireNonNull(name, "name must not be null");
    Objects.requireNonNull(document, "document must not be null");
    Objects.requireNonNull(email, "email must not be null");
    Objects.requireNonNull(address, "address must not be null");
    Objects.requireNonNull(birthDate, "birthDate must not be null");

    if (age < 0 || age > 100) {
      throw new IllegalArgumentException("the age must be between 0 and 100");
    }
    if (birthDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("The birthdate cannot be in the future");
    }
  }
}
