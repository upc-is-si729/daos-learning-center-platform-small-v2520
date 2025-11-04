package pe.edu.upc.center.platform.profiles.domain.model.commands;

import java.time.LocalDate;
import java.util.Objects;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.*;
import pe.edu.upc.center.platform.shared.utils.Util;

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
    Objects.requireNonNull(name, "[CreateProfileCommand] name must not be null");
    Objects.requireNonNull(document, "[CreateProfileCommand] document must not be null");
    Objects.requireNonNull(email, "[CreateProfileCommand] email must not be null");
    Objects.requireNonNull(address, "[CreateProfileCommand] address must not be null");
    Objects.requireNonNull(birthDate, "[CreateProfileCommand] birthDate must not be null");

    if (age < Util.MIN_AGE || age > Util.MAX_AGE) {
      throw new IllegalArgumentException(
          String.format("[CreateProfileCommand] The age must be between %s and %s",
              Util.MIN_AGE, Util.MAX_AGE));
    }
    if (birthDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("[CreateProfileCommand] The birthdate cannot be in the future");
    }
  }
}
