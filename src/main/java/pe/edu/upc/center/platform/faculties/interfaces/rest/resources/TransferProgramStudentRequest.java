package pe.edu.upc.center.platform.faculties.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TransferProgramStudentRequest(Long programId,
                                            Long curriculumId) {
  @JsonCreator
  public TransferProgramStudentRequest(@JsonProperty("programId") Long programId,
                                       @JsonProperty("curriculumId") Long curriculumId) {
    this.programId = programId;
    this.curriculumId = curriculumId;
  }
}
