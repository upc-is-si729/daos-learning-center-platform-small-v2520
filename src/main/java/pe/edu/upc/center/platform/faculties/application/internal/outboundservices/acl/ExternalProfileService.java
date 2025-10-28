package pe.edu.upc.center.platform.faculties.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.profiles.interfaces.acl.ProfilesContextFacade;

import java.util.Optional;

/**
 * Service class for interacting with external profile services via ProfilesContextFacade.
 */
@Service
public class ExternalProfileService {

  private final ProfilesContextFacade profilesContextFacade;

  /**
   * Constructor for ExternalProfileService.
   *
   * @param profilesContextFacade the ProfilesContextFacade to be used for profile operations
   */
  public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
    this.profilesContextFacade = profilesContextFacade;
  }

  public boolean existsProfileById(ProfileId profileId) {
    return this.profilesContextFacade.existsProfileById(profileId.profileId());
  }

  /**
   * Fetches a ProfileId by full name using the ProfilesContextFacade.
   *
   * @param firstName the first name of the profile
   * @param lastName  the last name of the profile
   * @return an Optional containing the ProfileId if found, or an empty Optional if not found
   */
  public Optional<ProfileId> fetchProfileIdByFullName(String firstName, String lastName) {
    var profileId = this.profilesContextFacade.fetchProfileIdByFullName(firstName, lastName);
    if (profileId.equals(0L)) {
      return Optional.empty();
    }
    return Optional.of(new ProfileId(profileId));
  }
}
