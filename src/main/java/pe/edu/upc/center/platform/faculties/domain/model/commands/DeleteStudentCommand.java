package pe.edu.upc.center.platform.faculties.domain.model.commands;

import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;

import java.util.Objects;

/**
 * Command to delete a student identified by their student code.
 *
 * @param studentCode The unique code of the student to be deleted.
 */
public record DeleteStudentCommand(StudentCode studentCode) {

  public DeleteStudentCommand {
    Objects.requireNonNull(studentCode, "The student's code must not be null");
  }
}
