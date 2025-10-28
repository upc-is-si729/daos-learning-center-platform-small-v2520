package pe.edu.upc.center.platform.faculties.domain.services;

import pe.edu.upc.center.platform.faculties.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.faculties.domain.model.commands.ChangeCurriculumStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.TransferProgramStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;

import java.util.Optional;

/**
 * Service interface for handling commands related to Student entities.
 */
public interface StudentCommandService {

  /**
   * Handles the creation of a new student based on the provided command.
   *
   * @param command the command containing the details of the student to be created
   * @return the StudentCode of the newly created student
   */
  StudentCode handle(CreateStudentCommand command);

  /**
   * Handles the update of a student based on the provided command.
   *
   * @param command the command containing the details of the student to be updated
   * @return an Optional containing the updated Student if the update was successful,
   *     or an empty Optional if not
   */
  Optional<Student> handle(TransferProgramStudentCommand command);

  /**
   * Handles the curriculum change of a student based on the provided command.
   *
   * @param command the command containing the details of the curriculum change
   * @return the ID of the student whose curriculum was changed
   */
  Optional<Student> handle(ChangeCurriculumStudentCommand command);

  /**
   * Handles the deletion of a student based on the provided command.
   *
   * @param command the command containing the details of the student to be deleted
   */
  void handle(DeleteStudentCommand command);
}
