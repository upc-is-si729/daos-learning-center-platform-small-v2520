package pe.edu.upc.center.platform.faculties.domain.model.queries;

import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.ProfileId;

import java.util.Objects;

/**
 * Query to get a student by their profile ID.
 *
 * @param profileId The profile ID of the student to retrieve.
 */
public record GetStudentByProfileIdQuery(ProfileId profileId) {

  public GetStudentByProfileIdQuery {
    Objects.requireNonNull(profileId, "The profile ID must not be null");
  }
}
