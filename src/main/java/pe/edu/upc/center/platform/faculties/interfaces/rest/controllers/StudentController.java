package pe.edu.upc.center.platform.faculties.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.faculties.domain.model.queries.GetAllStudentsQuery;
import pe.edu.upc.center.platform.faculties.domain.model.queries.GetStudentByCodeQuery;
import pe.edu.upc.center.platform.faculties.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.faculties.domain.services.StudentCommandService;
import pe.edu.upc.center.platform.faculties.domain.services.StudentQueryService;
import pe.edu.upc.center.platform.faculties.interfaces.rest.assemblers.StudentAssembler;
import pe.edu.upc.center.platform.faculties.interfaces.rest.resources.*;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileMinimalResponse;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.InternaServerErrorResponse;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.NotFoundResponse;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.ServiceUnavailableResponse;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
    RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "Student Management Endpoints")
public class StudentController {

  private final StudentCommandService studentCommandService;
  private final StudentQueryService studentQueryService;

  public StudentController(StudentCommandService studentCommandService,
      StudentQueryService studentQueryService) {
    this.studentCommandService = studentCommandService;
    this.studentQueryService = studentQueryService;
  }

  /**
   * Endpoint to create a new student.
   *
   * @param request the student data to be created
   * @return a ResponseEntity containing the created student resource or a bad request status
   *     if creation fails
   */
  @Operation(summary = "Create a new student",
      description = "Creates a new student with the provided data",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Student data for creation", required = true,
          content = @Content (
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = CreateStudentRequest.class)))
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Student created successfully",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = StudentResponse.class))),
      @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = BadRequestResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = InternaServerErrorResponse.class))),
      @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ServiceUnavailableResponse.class)))
  })
  @PostMapping
  public ResponseEntity<StudentResponse> createStudent(
      @Valid @RequestBody CreateStudentRequest request) {

    // Create student
    var createStudentCommand = StudentAssembler.toCommandFromRequest(request);
    var studentCode = this.studentCommandService.handle(createStudentCommand);

    // Validate if student code is null or blank
    if (Objects.isNull(studentCode) || studentCode.code().isBlank()) {
      return ResponseEntity.badRequest().build();
    }

    // Fetch student
    var getStudentByStudentCodeQuery = new GetStudentByCodeQuery(studentCode);
    var student = this.studentQueryService.handle(getStudentByStudentCodeQuery);
    if (student.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var studentResource = StudentAssembler.toResponseFromEntity(student.get());
    return new ResponseEntity<>(studentResource, HttpStatus.CREATED);
  }

  /**
   * Get all student profiles.
   *
   * @return a ResponseEntity containing a list of StudentResource
   */
  @Operation( summary = "Retrieve all students",
      description = "Retrieves all students in the system."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Students retrieved successfully",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(schema = @Schema(implementation = StudentResponse.class)) ))
  })
  @GetMapping
  public ResponseEntity<List<StudentResponse>> getAllStudents() {
    var getAllStudentsQuery = new GetAllStudentsQuery();
    var students = this.studentQueryService.handle(getAllStudentsQuery);
    var studentResponses = students.stream()
        .map(StudentAssembler::toResponseFromEntity)
        .collect(Collectors.toList());
    return ResponseEntity.ok(studentResponses);
  }

  /**
   * Get a student profile by student code.
   *
   * @param studentCode the student code of the profile to be retrieved
   * @return a ResponseEntity containing the StudentResource or a bad request status if not found
   */
  @Operation(summary = "Retrieve a Student by its ID",
      description = "Retrieves a Student using its unique ID",
      parameters = {
          @Parameter(in = ParameterIn.PATH, name = "studentCode",
              description = "Student code of the profile to retrieve",
              required = true,
              schema = @Schema(type = "string", format = "uuid"))
      }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Student retrieved successfully",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = StudentResponse.class))),
      @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = NotFoundResponse.class)))
  })
  @GetMapping("/{studentCode}")
  public ResponseEntity<StudentResponse> getStudentByStudentCode(@PathVariable String studentCode) {
    var getStudentByStudentCodeQuery = new GetStudentByCodeQuery(
        new StudentCode(studentCode));
    var optionalStudent = this.studentQueryService.handle(getStudentByStudentCodeQuery);
    var studentResponse = StudentAssembler.toResponseFromEntity(optionalStudent.get());
    return ResponseEntity.ok(studentResponse);
  }

  /**
   * Update a student profile by student code.
   *
   * @param studentCode the student code of the profile to be updated
   * @param request the data for updating the student's program
   * @return a ResponseEntity containing the updated StudentResource or a bad request status
   *     if the update fails
   */
  @Operation(summary = "Transfer a Student to another Program",
      description = "Transfers a Student to a different Program using its unique student code",
      parameters = {
          @Parameter(in = ParameterIn.PATH, name = "studentCode",
              description = "Student code of the profile to retrieve",
              required = true,
              schema = @Schema(type = "string", format = "uuid"))
      }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Student transferred successfully",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = StudentResponse.class))),
      @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = NotFoundResponse.class)))
  })
  @PutMapping("/{studentCode}/transfer")
  public ResponseEntity<StudentMinimalResponse> transferProgram(
      @PathVariable String studentCode,
      @Valid @RequestBody TransferProgramStudentRequest request) {
    var transferProgramCommand = StudentAssembler.toCommandFromTransferRequest(studentCode, request);
    var optionalStudent = this.studentCommandService.handle(transferProgramCommand);

    if (optionalStudent.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var studentMinimalResponse = StudentAssembler.toResponseMinimalFromEntity(optionalStudent.get());
    return ResponseEntity.ok(studentMinimalResponse);
  }

  @Operation(summary = "Transfer a Student to another Curriculum",
      description = "Transfers a Student to a different Curriculum using its unique student code",
      parameters = {
          @Parameter(in = ParameterIn.PATH, name = "studentCode",
              description = "Student code of the profile to retrieve",
              required = true,
              schema = @Schema(type = "string", format = "uuid"))
      }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Student curriculum changed successfully",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = StudentResponse.class))),
      @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = NotFoundResponse.class)))
  })
  @PutMapping("/{studentCode}/change")
  public ResponseEntity<StudentMinimalResponse> changeCurriculum(
      @PathVariable String studentCode,
      @Valid @RequestBody ChangeCurriculumStudentRequest request) {
    var changeCurriculumCommand = StudentAssembler.toCommandFromChangeRequest(studentCode, request);
    var optionalStudent = this.studentCommandService.handle(changeCurriculumCommand);

    if (optionalStudent.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var studentMinimalResponse = StudentAssembler.toResponseMinimalFromEntity(optionalStudent.get());
    return ResponseEntity.ok(studentMinimalResponse);
  }
}
