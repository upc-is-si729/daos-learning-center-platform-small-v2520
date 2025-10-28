package pe.edu.upc.center.platform.faculties.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to create a student.
 *
 * @param profileId   the ID of the profile associated with the student
 * @param programId   the ID of the program the student is enrolling in
 * @param curriculumId the ID of the curriculum the student will follow
 */
public record CreateStudentRequest(Long profileId, Long programId, Long curriculumId) {

  @JsonCreator
  public CreateStudentRequest(@JsonProperty("profileId") Long profileId,
                              @JsonProperty("programId") Long programId,
                              @JsonProperty("curriculumId") Long curriculumId) {
    this.profileId = profileId;
    this.programId = programId;
    this.curriculumId = curriculumId;
  }
}
