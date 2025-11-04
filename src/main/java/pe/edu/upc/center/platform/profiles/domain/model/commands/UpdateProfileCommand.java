package pe.edu.upc.center.platform.profiles.domain.model.commands;

import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.*;
import pe.edu.upc.center.platform.shared.utils.Util;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Command to update an existing user profile.
 *
 * @param profileId The ID of the profile to update.
 * @param name The name of the profile.
 * @param document The document of the profile.
 * @param birthDate The birthdate of the profile.
 * @param age The age of the profile.
 * @param email The email of the profile.
 * @param address The address of the profile.
 */
public record UpdateProfileCommand(Long profileId, PersonName name, Document document,
                                   LocalDate birthDate, int age,
                                   EmailAddress email, StreetAddress address) {

  public UpdateProfileCommand {
    Objects.requireNonNull(name, "[UpdateProfileCommand] name must not be null");
    Objects.requireNonNull(document, "[UpdateProfileCommand] document must not be null");
    Objects.requireNonNull(email, "[UpdateProfileCommand] email must not be null");
    Objects.requireNonNull(address, "[UpdateProfileCommand] address must not be null");
    Objects.requireNonNull(birthDate, "[UpdateProfileCommand] birthDate must not be null");

    if (profileId < 0) {
      throw new IllegalArgumentException("[UpdateProfileCommand] profileId must be greater than 0");
    }
    if (age < Util.MIN_AGE || age > Util.MAX_AGE) {
      throw new IllegalArgumentException(
          String.format("[UpdateProfileCommand] The age must be between %s and %s",
              Util.MIN_AGE, Util.MAX_AGE));
    }
    if (birthDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("[UpdateProfileCommand] The birthdate cannot be in the future");
    }
  }
}
