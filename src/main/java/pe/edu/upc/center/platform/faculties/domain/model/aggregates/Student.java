package pe.edu.upc.center.platform.faculties.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.platform.faculties.domain.model.commands.ChangeCurriculumStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.TransferProgramStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.CurriculumId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.ProgramId;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.center.platform.shared.utils.Util;

/**
 * Represents a Student entity in the learning platform.
 */
@Entity
@Table(name = "students")
public class Student extends AuditableAbstractAggregateRoot<Student> {

  @Getter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "studentCode",
          column = @Column(name = "code", length = 36, nullable = false))
  })
  private final StudentCode studentCode;

  @Getter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "profileId",
          column = @Column(name = "profile_id", nullable = false))
  })
  private ProfileId profileId;

  @Getter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "programId",
          column = @Column(name = "program_id", nullable = false))
  })
  private ProgramId programId;

  @Getter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "curriculumId",
          column = @Column(name = "curriculum_id", nullable = false))
  })
  private CurriculumId curriculumId;

  @Getter
  @Column(name = "start_period", length = 6, nullable = false)
  private String startPeriod;

  /**
   * Default constructor for JPA.
   */
  public Student() {
    this.studentCode = new StudentCode();
  }

  /**
   * Constructs a Student instance with a specified profile ID.
   *
   * @param profileId The profile ID associated with the student.
   */
  public Student(Long profileId) {
    this();
    this.profileId = new ProfileId(profileId);
  }

  /**
   * Constructs a Student instance based on the CreateStudentCommand.
   *
   * @param command The command containing student creation details.
   */
  public Student(CreateStudentCommand command) {
    this();
    this.profileId = new ProfileId(command.profileId());
    this.programId = new ProgramId(command.programId());
    this.curriculumId = new CurriculumId(command.curriculumId());
    this.startPeriod = Util.START_PERIOD_ACTUAL;
  }
  /**
   * Constructs a Student instance with specified profile ID, program ID, and start period.
   *
   * @param profileId The profile ID associated with the student.
   * @param programId The program ID the student is enrolled in.
   * @param startPeriod The period when the student started.
   */
  public Student(ProfileId profileId, Long programId, String startPeriod) {
    this();
    this.profileId = profileId;
    this.programId = new ProgramId(programId);
    this.startPeriod = startPeriod;
  }

  /**
   * Updates the student's program and curriculum.
   *
    * @param command the command with the new program and curriculum
   */
  public void updateProgram(TransferProgramStudentCommand command) {
    this.programId = new ProgramId(command.programId());
    this.curriculumId = new CurriculumId(command.curriculumId());
    this.startPeriod = Util.START_PERIOD_ACTUAL;
  }

  /**
   * Updates the student's curriculum.
   * @param command the command with the new curriculum
   */
  public void updateCurriculum(ChangeCurriculumStudentCommand command) {
    this.curriculumId = new CurriculumId(command.curriculumId());
  }

}
