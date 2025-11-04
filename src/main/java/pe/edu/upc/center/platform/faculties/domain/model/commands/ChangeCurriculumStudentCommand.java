package pe.edu.upc.center.platform.faculties.domain.model.commands;

import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.CurriculumId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;

import java.util.Objects;

/**
 * Command to change the curriculum of a student.
 *
 * @param studentCode  The unique code of the student.
 * @param curriculumId The ID of the new curriculum to assign to the student.
 */
public record ChangeCurriculumStudentCommand(StudentCode studentCode, CurriculumId curriculumId) {

  public ChangeCurriculumStudentCommand {
    Objects.requireNonNull(studentCode, "The student's code must not be null");
    Objects.requireNonNull(curriculumId, "The curriculum ID must not be null");
  }
}
