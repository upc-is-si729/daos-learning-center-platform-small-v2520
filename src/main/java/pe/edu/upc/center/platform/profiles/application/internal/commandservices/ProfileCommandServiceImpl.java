package pe.edu.upc.center.platform.profiles.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.domain.exceptions.ProfileNotfoundException;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
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

    // Validate if the profile already exists
    if (this.profileRepository.existsByEmail(command.email())) {
      throw new IllegalArgumentException("Profile with email " + command.email()
          + " already exists");
    }
    if (this.profileRepository.existsByDocument(command.document())) {
      throw new IllegalArgumentException("Profile with document " + command.document()
          + " already exists");
    }
    // Create the profile
    var profile = new Profile(command);
    try {
      this.profileRepository.save(profile);
    } catch (Exception e) {
      throw new PersistenceException("Error while saving profile: " + e.getMessage());
    }
    return profile.getId();
  }

  @Override
  public Optional<Profile> handle(UpdateProfileCommand command) {

    // Validate if the profile already exists
    var profileId = command.profileId();
    if (!this.profileRepository.existsById(profileId)) {
      throw new ProfileNotfoundException(profileId);
    }
    if (this.profileRepository.existsByEmailAndIdIsNot(command.email(), profileId)) {
      throw new IllegalArgumentException("Profile with email " + command.email()
          + " already exists");
    }
    if (this.profileRepository.existsByDocumentAndIdIsNot(command.document(), profileId)) {
      throw new IllegalArgumentException("Profile with document " + command.document()
          + " already exists");
    }

    // Update the profile
    var profileToUpdate = this.profileRepository.findById(profileId).get();
    profileToUpdate.updateProfile(command);

    try {
      var updatedProfile = this.profileRepository.save(profileToUpdate);
      return Optional.of(updatedProfile);
    } catch (Exception e) {
      throw new PersistenceException("Error while updating profile: " + e.getMessage());
    }
  }

  @Override
  public void handle(DeleteProfileCommand command) {
    // If the profile does not exist, throw an exception
    if (!this.profileRepository.existsById(command.profileId())) {
      throw new ProfileNotfoundException(command.profileId());
    }

    // Try to delete the profile, if an error occurs, throw an exception
    try {
      this.profileRepository.deleteById(command.profileId());
    } catch (Exception e) {
      throw new PersistenceException("Error while deleting profile: " + e.getMessage());
    }
  }
}
