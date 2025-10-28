package pe.edu.upc.center.platform.faculties.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value object representing a Profile ID.
 *
 * @param profileId the unique identifier for the profile
 */
@Embeddable
public record ProfileId(Long profileId) {
  /**
   * Constructor for ProfileId with validation.
   *
   * @param profileId the unique identifier for the profile
   * @throws IllegalArgumentException if profileId is negative
   */
  public ProfileId {
    if (Objects.isNull(profileId) || profileId < 0) {
      throw new IllegalArgumentException("Profile ID cannot be null or negative");
    }
  }

  /**
   * Default constructor for ProfileId with a default value of 0.
   */
  public ProfileId() {
    this(0L);
  }
}
