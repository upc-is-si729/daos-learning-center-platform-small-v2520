package pe.edu.upc.center.platform.profiles.application.internal.queryservices;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.queries.*;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

/**
 * Implementation of the ProfileQueryService interface.
 */
@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

  private final ProfileRepository profileRepository;

  /**
   * Constructor for ProfileQueryServiceImpl.
   *
   * @param profileRepository the repository to access profile data
   */
  public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  @Override
  public List<Profile> handle(GetAllProfilesQuery query) {
    return this.profileRepository.findAll();
  }

  @Override
  public Optional<Profile> handle(GetProfileByIdQuery query) {
    return this.profileRepository.findById(query.profileId());
  }

  @Override
  public Optional<Profile> handle(GetProfileByNameQuery query) {
    return this.profileRepository.findByName(query.name());
  }

  @Override
  public List<Profile> handle(GetProfileByAgeQuery query) {
    return this.profileRepository.findByAge(query.age());
  }

  @Override
  public Optional<Profile> handle(GetProfileByEmailQuery query) {
    return this.profileRepository.findByEmail(query.email());
  }

  @Override
  public boolean handle(ExistsProfileByIdQuery query) {
    return this.profileRepository.existsById(query.profileId());
  }

}
