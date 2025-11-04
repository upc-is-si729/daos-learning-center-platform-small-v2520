package pe.edu.upc.center.platform.faculties.domain.model.commands;

import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.CurriculumId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.ProgramId;

import java.util.Objects;

/**
 * Command to create a new student.
 *
 * @param profileId   the ID of the profile associated with the student
 * @param programId   the ID of the program the student is enrolling in
 * @param curriculumId the ID of the curriculum the student will follow
 */
public record CreateStudentCommand(ProfileId profileId, ProgramId programId,
                                   CurriculumId curriculumId) {

  public CreateStudentCommand {
    Objects.requireNonNull(profileId, "The profile ID must not be null");
    Objects.requireNonNull(programId, "The program ID must not be null");
    Objects.requireNonNull(curriculumId, "The curriculum ID must not be null");
  }
}
