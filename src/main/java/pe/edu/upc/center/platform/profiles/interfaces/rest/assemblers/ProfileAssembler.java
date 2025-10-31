package pe.edu.upc.center.platform.profiles.interfaces.rest.assemblers;

import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.*;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateProfileRequest;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileMinimalResponse;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResponse;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.UpdateProfileRequest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class ProfileAssembler {

  public static CreateProfileCommand toCommandFromRequest(CreateProfileRequest request) {

    return new CreateProfileCommand(new PersonName(request.firstName(), request.lastName()),
        new Document(DocumentTypes.fromValue(request.documentType()), request.documentNumber()),
        request.birthDate(), calculateAge(request.birthDate()),
        new EmailAddress(request.email()),
        new StreetAddress(request.street(), request.streetNumber(), request.city(),
            request.postalCode(), request.country())
    );
    /*return new CreateProfileCommand( request.firstName(), request.lastName(),
        DocumentTypes.fromValue(request.documentType()), request.documentNumber(),
        request.birthDate(), calculateAge(request.birthDate()),
        request.email(), request.street(), request.streetNumber(), request.city(),
        request.postalCode(), request.country());*/
  }

  public static UpdateProfileCommand toCommandFromRequest(Long profileId,
                                                          UpdateProfileRequest request) {

    return new UpdateProfileCommand( profileId,
        new PersonName(request.firstName(), request.lastName()),
        new Document(DocumentTypes.fromValue(request.documentType()), request.documentNumber()),
        request.birthDate(), calculateAge(request.birthDate()),
        new EmailAddress(request.email()),
        new StreetAddress(request.street(), request.streetNumber(), request.city(),
            request.postalCode(), request.country()));
  }

  public static ProfileResponse toResponseFromEntity(Profile entity) {

    return new ProfileResponse(entity.getId(), entity.getName().firstName(),
        entity.getName().lastName(), entity.getDocument().type().getValue(),
        entity.getDocument().number(), entity.getBirthDate(),
        entity.getAge(), entity.getEmail().address(),
        entity.getAddress().street(), entity.getAddress().number(),
        entity.getAddress().city(), entity.getAddress().postalCode(),
        entity.getAddress().country());
  }
  public static ProfileMinimalResponse toResponseMinimalFromEntity(Profile entity) {
    return new ProfileMinimalResponse(entity.getId(), entity.getFullName(),
        entity.getFullDocument(), entity.getBirthDate().toString(),
        entity.getEmail().address(), entity.getFullAddress());
  }

  public static int calculateAge(LocalDate birthDate) {
    if (Objects.isNull(birthDate)) {
      return 0;
    }
    return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
  }

  public static CreateProfileCommand toCommandFromValues(
      String firstName, String lastName, int documentType, String documentNumber,
      LocalDate birthDate, String email, String street, String streetNumber, String city,
      String postalCode, String country) {

    return new CreateProfileCommand(new PersonName(firstName, lastName),
        new Document(DocumentTypes.fromValue(documentType), documentNumber),
        birthDate, calculateAge(birthDate),
        new EmailAddress(email),
        new StreetAddress(street, streetNumber, city, postalCode, country));
  }

  public static UpdateProfileCommand toCommandFromValues(
      Long profileId, String firstName, String lastName, int documentType, String documentNumber,
      LocalDate birthDate, String email, String street, String streetNumber, String city,
      String postalCode, String country) {

    return new UpdateProfileCommand(profileId, new PersonName(firstName, lastName),
        new Document(DocumentTypes.fromValue(documentType), documentNumber),
        birthDate, calculateAge(birthDate),
        new EmailAddress(email),
        new StreetAddress(street, streetNumber, city, postalCode, country));
  }
}
