package pe.edu.upc.center.platform.faculties.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChangeCurriculumStudentRequest(
    @JsonProperty("curriculumId")
    @NotNull @Positive
    Long curriculumId
) {
}
