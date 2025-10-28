package pe.edu.upc.center.platform.profiles.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.PersonName;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.Optional;

/**
 * Implementation of ProfileCommandService.
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

  private final ProfileRepository profileRepository;

  /**
   * Constructor for ProfileCommandServiceImpl.
   *
   * @param profileRepository the profile repository
   */
  public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  @Override
  public Long handle(CreateProfileCommand command) {
    var fullName = new PersonName(command.firstName(), command.lastName());
    if (this.profileRepository.existsByName(fullName)) {
      throw new IllegalArgumentException("Profile with full name " + fullName + " already exists");
    }
    var profile = new Profile(command);
    try {
      this.profileRepository.save(profile);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while saving profile: " + e.getMessage());
    }
    return profile.getId();
  }

  @Override
  public Optional<Profile> handle(UpdateProfileCommand command) {
    var profileId = command.profileId();
    var fullName = new PersonName(command.firstName(), command.lastName());
    if (this.profileRepository.existsByNameAndIdIsNot(fullName, profileId)) {
      throw new IllegalArgumentException("Profile with full name " + fullName + " already exists");
    }

    // If the profile does not exist, throw an exception
    if (!this.profileRepository.existsById(profileId)) {
      throw new IllegalArgumentException("Profile with id " + profileId + " does not exist");
    }

    var profileToUpdate = this.profileRepository.findById(profileId).get();
    profileToUpdate.updateProfile(command);

    try {
      var updatedProfile = this.profileRepository.save(profileToUpdate);
      return Optional.of(updatedProfile);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while updating profile: " + e.getMessage());
    }
  }

  @Override
  public void handle(DeleteProfileCommand command) {
    // If the profile does not exist, throw an exception
    if (!this.profileRepository.existsById(command.profileId())) {
      throw new IllegalArgumentException("Profile with id " + command.profileId()
          + " does not exist");
    }

    // Try to delete the profile, if an error occurs, throw an exception
    try {
      this.profileRepository.deleteById(command.profileId());
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while deleting profile: " + e.getMessage());
    }
  }
}
