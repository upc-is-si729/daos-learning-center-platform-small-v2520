package pe.edu.upc.center.platform.profiles.interfaces.rest.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.upc.center.platform.profiles.domain.exceptions.ProfileNotfoundException;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.NotFoundExceptionResponse;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.ValidationExceptionResponse;

import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "pe.edu.upc.center.platform.profiles" )
public class ProfileExceptionHandler {

  @ExceptionHandler(DateTimeParseException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ValidationExceptionResponse> handleDateTimeParseException(
      DateTimeParseException ex) {

    Map<String, String> fieldErrors = new LinkedHashMap<>();
    fieldErrors.put("birthDate", "Invalid date format (YYYY-MM-DD): " + ex.getMessage());

    var response = new ValidationExceptionResponse(
        HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Date parsing failed", fieldErrors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(ProfileNotfoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<NotFoundExceptionResponse> handleProfileNotfoundException(
      ProfileNotfoundException ex) {
    var response = new NotFoundExceptionResponse(
        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }


}
