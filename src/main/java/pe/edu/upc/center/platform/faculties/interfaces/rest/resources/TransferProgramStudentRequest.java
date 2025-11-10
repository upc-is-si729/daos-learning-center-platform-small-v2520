package pe.edu.upc.center.platform.faculties.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferProgramStudentRequest(
    @JsonProperty("programId")
    @NotNull @Positive
    Long programId,

    @JsonProperty("curriculumId")
    @NotNull @Positive
    Long curriculumId) {
}
