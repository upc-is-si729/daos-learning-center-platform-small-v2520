package pe.edu.upc.center.platform.faculties.domain.model.queries;

import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;

import java.util.Objects;

/**
 * Query to get a student by their student code.
 *
 * @param code The unique code of the student.
 */
public record GetStudentByCodeQuery(StudentCode code) {

  public GetStudentByCodeQuery {
    Objects.requireNonNull(code, "The student's code must not be null");
  }
}
