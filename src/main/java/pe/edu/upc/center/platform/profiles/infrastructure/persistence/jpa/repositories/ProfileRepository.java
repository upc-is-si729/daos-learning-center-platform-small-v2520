package pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.PersonName;

import java.util.List;
import java.util.Optional;

/** Repository interface for managing Profile entities.
 *
 * <p>This interface extends JpaRepository to provide CRUD operations and custom query methods
 *     for the Profile entity.</p>
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  /** Custom query method to check existence of a profile by full name.
   *
   * @param name the full name to check for existence
   * @return true if a profile with the given full name exists, false otherwise
   */
  boolean existsByName(PersonName name);

  /** Custom query method to check existence of a profile by full name excluding a specific ID.
   *
   * @param name the last name to check for existence
   * @param id the ID to exclude from the check
   * @return true if a profile with the given last name exists excluding the specified ID,
   *     false otherwise
   */
  boolean existsByNameAndIdIsNot(PersonName name, Long id);

  /** Custom query method to find a profile by full name.
   *
   * @param name the full name to search for
   * @return an Optional containing the found Profile if found, or empty if not found
   */
  Optional<Profile> findByName(PersonName name);

  /** Custom query method to find profiles by age.
   *
   * @param age the age to filter profiles
   * @return a list of profiles matching the specified age
   */
  List<Profile> findByAge(int age);
}
