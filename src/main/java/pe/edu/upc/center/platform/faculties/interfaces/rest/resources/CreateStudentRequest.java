package pe.edu.upc.center.platform.faculties.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request to create a student.
 *
 * @param profileId   the ID of the profile associated with the student
 * @param programId   the ID of the program the student is enrolling in
 * @param curriculumId the ID of the curriculum the student will follow
 */
public record CreateStudentRequest(
    @JsonProperty("profileId")
    @NotNull @Positive
    Long profileId,

    @JsonProperty("programId")
    @NotNull @Positive
    Long programId,

    @JsonProperty("curriculumId")
    @NotNull @Positive
    Long curriculumId) {
}
