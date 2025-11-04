package pe.edu.upc.center.platform.faculties.domain.model.commands;

import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.CurriculumId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.ProgramId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;

import java.util.Objects;

/**
 * Command to transfer a student to a program and curriculum.
 *
 * @param studentCode the code of the student
 * @param programId   the ID of the program the student is enrolling in
 * @param curriculumId the ID of the curriculum the student will follow
 */
public record TransferProgramStudentCommand(StudentCode studentCode, ProgramId programId,
                                            CurriculumId curriculumId) {

  public TransferProgramStudentCommand {
    Objects.requireNonNull(studentCode, "The student's code must not be null");
    Objects.requireNonNull(programId, "The program ID must not be null");
    Objects.requireNonNull(curriculumId, "The curriculum ID must not be null");
  }
}
