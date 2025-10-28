package pe.edu.upc.center.platform.faculties.application.internal.commandservices;

import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.faculties.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.platform.faculties.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.faculties.domain.model.commands.ChangeCurriculumStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.TransferProgramStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.faculties.domain.services.StudentCommandService;
import pe.edu.upc.center.platform.faculties.infrastructure.persistence.jpa.repositories.StudentRepository;

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
   * @param externalProfileService the external service for managing profiles
   */
  public StudentCommandServiceImpl(StudentRepository studentRepository,
                                   ExternalProfileService externalProfileService) {
    this.studentRepository = studentRepository;
    this.externalProfileService = externalProfileService;
  }

  @Override
  public StudentCode handle(CreateStudentCommand command) {
    var profileId = new ProfileId(command.profileId());
    // Validate if profile ID already exists
    if (this.studentRepository.existsByProfileId(profileId)) {
      throw new IllegalArgumentException("Student with profile ID already exists");
    }

    // Validate if profile ID exists in external Profile Service
    if (!this.externalProfileService.existsProfileById(profileId)) {
      throw  new IllegalArgumentException("Profile ID does not exist in external Profile Service");
    }

    var student = new Student(command);
    try {
      var createdStudent = this.studentRepository.save(student);
      return createdStudent.getStudentCode();
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to create student: " + e.getMessage());
    }
  }

  @Override
  public Optional<Student> handle(TransferProgramStudentCommand command) {

    // validate if a student exists
    if (!this.studentRepository.existsByStudentCode(command.studentCode())) {
      throw new IllegalArgumentException("Student not found with code " + command.studentCode());
    }
    // validate if a program exists

    var studentToUpdate = this.studentRepository.findByStudentCode(command.studentCode()).get();
    studentToUpdate.updateProgram(command);

    try {
      var updatedStudent = this.studentRepository.save(studentToUpdate);
      return Optional.of(updatedStudent);
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Error while updating student program: " + e.getMessage());
    }
  }

  @Override
  public Optional<Student> handle(ChangeCurriculumStudentCommand command) {

    try {
      this.studentRepository.findByStudentCode(command.studentCode()).map(student -> {
        student.updateCurriculum(command);
        return Optional.of(this.studentRepository.save(student));
      }).orElseThrow(() ->
          new IllegalArgumentException("Student not found with code " + command.studentCode()));
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Error while updating student curriculum: " + e.getMessage());
    }
    return null;
  }

  @Override
  public void handle(DeleteStudentCommand command) {
    // validate if a student exists
    if (!this.studentRepository.existsByStudentCode(command.studentCode())) {
      throw new IllegalArgumentException("Student not found");
    }

    this.studentRepository.findByStudentCode(command.studentCode()).ifPresent(optionalStudent -> {
      this.studentRepository.deleteById(optionalStudent.getId());
    });
  }
}
