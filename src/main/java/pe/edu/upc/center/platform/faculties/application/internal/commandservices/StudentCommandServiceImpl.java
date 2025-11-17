package pe.edu.upc.center.platform.faculties.application.internal.commandservices;

import java.util.Optional;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.platform.faculties.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.platform.faculties.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.faculties.domain.model.commands.ChangeCurriculumStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.TransferProgramStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.faculties.domain.services.StudentCommandService;
import pe.edu.upc.center.platform.faculties.infrastructure.persistence.jpa.repositories.StudentRepository;
import pe.edu.upc.center.platform.shared.domain.exceptions.NotFoundArgumentException;

/**
 * Implementation of the StudentCommandService interface for handling student-related commands.
 *
 * <p>This service provides methods to create, update, and delete student entities, interacting
 * with both the StudentRepository and an external profile service.</p>
 */
@Service
public class StudentCommandServiceImpl implements StudentCommandService {

  private final StudentRepository studentRepository;
  private final ExternalProfileService externalProfileService;

  /**
   * Constructs a StudentCommandServiceImpl with the specified dependencies.
   *
   * @param studentRepository the repository for managing Student entities
   * @param externalProfileService an external service for managing profiles
   */
  public StudentCommandServiceImpl(StudentRepository studentRepository,
                                   ExternalProfileService externalProfileService) {
    this.studentRepository = studentRepository;
    this.externalProfileService = externalProfileService;
  }

  @Override
  public StudentCode handle(CreateStudentCommand command) {
    var profileId = command.profileId();
    // Validate if profile ID already exists
    if (this.studentRepository.existsByProfileId(profileId)) {
      throw new IllegalArgumentException("Student with profile ID already exists");
    }

    // Validate if profile ID not exists in external profile service
    if (!this.externalProfileService.existsProfileById(profileId)) {
      throw new NotFoundArgumentException(
          String.format("Profile ID: %s, not found in external Profile service: ", profileId.profileId()));
    }

    var student = new Student(command);
    try {
      var createdStudent = this.studentRepository.save(student);
      return createdStudent.getCode();
    } catch (Exception e) {
      throw new PersistenceException("Error while creating student: " + e.getMessage());
    }
  }

  @Override
  public Optional<Student> handle(TransferProgramStudentCommand command) {

    // Find and validate if a student exists
    var studentToUpdate = this.studentRepository.findByCode(command.studentCode())
        .orElseThrow(() -> new NotFoundArgumentException(
            String.format("Student not found with code: %s ",command.studentCode().code())));

    studentToUpdate.updateProgram(command);

    try {
      var updatedStudent = this.studentRepository.save(studentToUpdate);
      return Optional.of(updatedStudent);
    }
    catch (Exception e) {
      throw new PersistenceException("Error while updating student program: " + e.getMessage());
    }
  }

  @Override
  public Optional<Student> handle(ChangeCurriculumStudentCommand command) {

    return this.studentRepository.findByCode(command.studentCode())
        .map(student -> {
          student.updateCurriculum(command);
          try {
            return Optional.of(this.studentRepository.save(student));
          } catch (Exception e) {
            throw new PersistenceException("Error while updating student curriculum: " + e.getMessage());
          }
        })
        .orElseThrow(() ->
            new NotFoundArgumentException("Student not found with code: "
                + command.studentCode().code()));

  }

  @Override
  public void handle(DeleteStudentCommand command) {
    // validate if a student exists
    if (!this.studentRepository.existsByCode(command.studentCode())) {
      throw new NotFoundArgumentException(
          String.format("Student not found with code %s", command.studentCode().code()));
    }

    try {
      this.studentRepository.findByCode(command.studentCode())
          .ifPresent(student -> {
            this.studentRepository.deleteById(student.getId());
          });
    } catch (Exception e) {
      throw new PersistenceException("Error while deleting student: " + e.getMessage());
    }

  }
}
