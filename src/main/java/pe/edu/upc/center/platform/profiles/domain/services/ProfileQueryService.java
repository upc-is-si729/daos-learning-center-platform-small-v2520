package pe.edu.upc.center.platform.profiles.domain.services;

import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling profile-related queries.
 */
public interface ProfileQueryService {

  /**
   * Handle the query to get all profiles.
   *
   * @param query the query to get all profiles
   * @return a list of all profiles
   */
  List<Profile> handle(GetAllProfilesQuery query);

  /**
   * Handle the query to get a profile by its ID.
   *
   * @param query the query containing the profile ID
   * @return an optional profile matching the ID
   */
  Optional<Profile> handle(GetProfileByIdQuery query);

  /** Handle the query to get a profile by its name.
   *
   * @param query the query containing the profile name
   * @return an optional profile matching the name
   */
  Optional<Profile> handle(GetProfileByNameQuery query);

  /**
   * Handle the query to get profiles by age.
   *
   * @param query the query containing the age criteria
   * @return a list of profiles matching the age criteria
   */
  List<Profile> handle(GetProfileByAgeQuery query);

  /**
   * Handle the query to get a profile by its email.
   * @param query the query containing the profile email
   * @return an optional profile matching the email
   */
  Optional<Profile> handle(GetProfileByEmailQuery query);

  /**
   * Handle the query to check if a profile exists by its ID.
   *
   * @param query the query containing the profile ID
   * @return true if the profile exists, false otherwise
   */
  boolean handle(ExistsProfileByIdQuery query);
}
