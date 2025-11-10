package pe.edu.upc.center.platform.faculties.application.internal.queryservices;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.faculties.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.faculties.domain.model.queries.GetAllStudentsQuery;
import pe.edu.upc.center.platform.faculties.domain.model.queries.GetStudentByIdQuery;
import pe.edu.upc.center.platform.faculties.domain.model.queries.GetStudentByProfileIdQuery;
import pe.edu.upc.center.platform.faculties.domain.model.queries.GetStudentByCodeQuery;
import pe.edu.upc.center.platform.faculties.domain.services.StudentQueryService;
import pe.edu.upc.center.platform.faculties.infrastructure.persistence.jpa.repositories.StudentRepository;
import pe.edu.upc.center.platform.shared.domain.exceptions.NotFoundArgumentException;
import pe.edu.upc.center.platform.shared.domain.exceptions.NotFoundIdException;

/**
 * Implementation of the StudentQueryService interface.
 */
@Service
public class StudentQueryServiceImpl implements StudentQueryService {

  private final StudentRepository studentRepository;

  /**
   * Constructor for StudentQueryServiceImpl.
   *
   * @param studentRepository the repository used to access student data
   */
  public StudentQueryServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public List<Student> handle(GetAllStudentsQuery query) {
    return this.studentRepository.findAll();
  }

  @Override
  public Optional<Student> handle(GetStudentByIdQuery query) {
    return Optional.ofNullable(this.studentRepository.findById(query.studentId())
        .orElseThrow(() -> new NotFoundIdException(Student.class, query.studentId())));
  }

  @Override
  public Optional<Student> handle(GetStudentByCodeQuery query) {
    return Optional.ofNullable(this.studentRepository.findByCode(query.code())
        .orElseThrow(() -> new NotFoundArgumentException("Student not found with code: "
            + query.code().code())));
  }

  @Override
  public Optional<Student> handle(GetStudentByProfileIdQuery query) {
    return Optional.ofNullable(this.studentRepository.findByProfileId(query.profileId())
        .orElseThrow(() -> new NotFoundArgumentException("Student not found with profile ID: "
            + query.profileId().profileId())));
  }
}
