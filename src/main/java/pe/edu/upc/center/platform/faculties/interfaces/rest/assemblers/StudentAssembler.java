package pe.edu.upc.center.platform.faculties.interfaces.rest.assemblers;

import pe.edu.upc.center.platform.faculties.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.faculties.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.commands.TransferProgramStudentCommand;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.faculties.interfaces.rest.resources.CreateStudentRequest;
import pe.edu.upc.center.platform.faculties.interfaces.rest.resources.StudentMinimalResponse;
import pe.edu.upc.center.platform.faculties.interfaces.rest.resources.StudentResponse;
import pe.edu.upc.center.platform.faculties.interfaces.rest.resources.TransferProgramStudentRequest;

public class StudentAssembler {

  public static CreateStudentCommand toCommandFromTransferRequest(CreateStudentRequest request) {

    return new CreateStudentCommand(request.profileId(), request.programId(),
        request.curriculumId());
  }

  public static TransferProgramStudentCommand toCommandFromTransferRequest(
      String studentCode, TransferProgramStudentRequest request) {

    return new TransferProgramStudentCommand(new StudentCode(studentCode),
        request.programId(), request.curriculumId());
  }

  public static StudentResponse toResponseFromEntity(Student entity) {

    return new StudentResponse(entity.getStudentCode().studentCode(),
        entity.getProfileId().profileId(),
        entity.getProgramId().programId(),
        entity.getCurriculumId().curriculumId(), entity.getStartPeriod());
  }

  public static StudentMinimalResponse toResponseMinimalFromEntity(Student entity) {

    return new StudentMinimalResponse(entity.getStudentCode().studentCode(),
        entity.getProgramId().programId(), entity.getCurriculumId().curriculumId(),
        entity.getStartPeriod());
  }

}
