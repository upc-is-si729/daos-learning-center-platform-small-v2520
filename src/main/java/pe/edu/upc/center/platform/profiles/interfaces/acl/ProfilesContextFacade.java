package pe.edu.upc.center.platform.profiles.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.queries.ExistsProfileByIdQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByNameQuery;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.PersonName;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.center.platform.profiles.interfaces.rest.assemblers.ProfileAssembler;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResponse;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * Facade for managing profiles, providing methods to create, update, delete, and fetch profiles.
 */
@Service
public class ProfilesContextFacade {
  private final ProfileCommandService profileCommandService;
  private final ProfileQueryService profileQueryService;

  /**
   * Constructs a ProfilesContextFacade with the specified command and query services.
   *
   * @param profileCommandService the service for handling profile commands
   * @param profileQueryService   the service for handling profile queries
   */
  public ProfilesContextFacade(ProfileCommandService profileCommandService,
                               ProfileQueryService profileQueryService) {
    this.profileCommandService = profileCommandService;
    this.profileQueryService = profileQueryService;
  }

  /**
   * Checks if a profile with the given ID exists.
   *
   * @param profileId the ID of the profile to check
   * @return true if the profile exists, false otherwise
   */
  public boolean existsProfileById(Long profileId) {
    var existsProfileByIdQuery = new ExistsProfileByIdQuery(profileId);
    return this.profileQueryService.handle(existsProfileByIdQuery);
  }

  /**
   * Fetches a profile by its ID.
   *
   * @param profileId the ID of the profile to fetch
   * @return an Optional containing the ProfileResource if found, otherwise an empty Optional
   */
  public Optional<ProfileResponse> fetchProfileById(Long profileId) {
    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = profileQueryService.handle(getProfileByIdQuery);
    if (optionalProfile.isEmpty()) {
      return Optional.empty();
    }
    var profileResponse = ProfileAssembler.toResponseFromEntity(optionalProfile.get());
    return Optional.of(profileResponse);
  }

  /**
   * Fetches the profile ID by full name.
   *
   * @param firstName the first name of the profile
   * @param lastName  the last name of the profile
   * @return the profile ID if found, otherwise returns 0L
   */
  public Long fetchProfileIdByFullName(String firstName, String lastName) {
    var getProfileByFullNameQuery = new GetProfileByNameQuery(new PersonName(firstName, lastName));
    var optionalProfile = profileQueryService.handle(getProfileByFullNameQuery);
    if (optionalProfile.isEmpty()) {
      return 0L;
    }
    return optionalProfile.get().getId();
  }

  /**
   * Checks if a profile with the given full name exists, excluding the profile with
   *     the specified ID.
   *
   * @param firstName the first name of the profile
   * @param lastName  the last name of the profile
   * @param id       the ID of the profile to exclude from the check
   * @return true if a profile with the given full name exists and has a different ID,
   *     false otherwise
   */
  public boolean existsProfileByFullNameAndIdIsNot(String firstName, String lastName, Long id) {
    var getProfileByFullNameQuery = new GetProfileByNameQuery(new PersonName(firstName, lastName));
    var optionalProfile = profileQueryService.handle(getProfileByFullNameQuery);
    if (optionalProfile.isEmpty()) {
      return false;
    }
    return optionalProfile.get().getId() != id;
  }

  /**
   * Creates a new profile.
   *
   * @param firstName The first name of the profile.
   * @param lastName The last name of the profile.
   * @param documentType The type of document of the profile.
   * @param documentNumber The number of documents of the profile.
   * @param birthDate The birthdate of the profile.
   * @param email The email of the profile.
   * @param street The street address of the profile.
   * @param streetNumber The number the street address of the profile.
   * @param city The city street address of the profile.
   * @param postalCode The postal code the street address of the profile.
   * @param country The country the street address of the profile.
   */
  public Long createProfile(String firstName, String lastName,
                            int documentType, String documentNumber, LocalDate birthDate,
                            String email, String street, String streetNumber,
                            String city, String postalCode, String country) {
    var createProfileCommand = ProfileAssembler.toCommandFromValues(firstName, lastName,
        documentType, documentNumber, birthDate, email, street, streetNumber,
        city, postalCode, country);

    var profileId = profileCommandService.handle(createProfileCommand);
    if (Objects.isNull(profileId)) {
      return 0L;
    }
    return profileId;
  }

  /**
   * Updates an existing profile.
   *
   * @param profileId the ID of the profile to update
   * @param firstName The first name of the profile.
   * @param lastName The last name of the profile.
   * @param age The age of the profile.
   * @param email The email of the profile.
   * @param street The street address of the profile.
   * @param number The number of the street address of the profile.
   * @param city The city of street address of the profile.
   * @param postalCode The postal code of the street address of the profile.
   * @param country The country of the street address of the profile.
   * @return the ID of the updated profile, or 0L if the update failed
   */
  public Long updateProfile(Long profileId, String firstName, String lastName,
                            int documentType, String documentNumber, LocalDate birthDate,
                            String email, String street, String streetNumber,
                            String city, String postalCode, String country) {

    var updateProfileCommand = ProfileAssembler.toCommandFromValues(profileId, firstName,
        lastName, documentType, documentNumber, birthDate, email, street, streetNumber,
        city, postalCode, country);
    var optionalProfile = profileCommandService.handle(updateProfileCommand);
    if (optionalProfile.isEmpty()) {
      return 0L;
    }
    return optionalProfile.get().getId();
  }

  /**
   * Deletes a profile by its ID.
   *
   * @param profileId the ID of the profile to delete
   */
  public void deleteProfile(Long profileId) {
    var deleteProfileCommand = new DeleteProfileCommand(profileId);
    profileCommandService.handle(deleteProfileCommand);
  }
}
