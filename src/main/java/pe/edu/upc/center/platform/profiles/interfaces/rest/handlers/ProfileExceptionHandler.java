package pe.edu.upc.center.platform.profiles.interfaces.rest.handlers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.BadRequestResponse;

import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for profile-related exceptions.
 */
@RestControllerAdvice(basePackages = "pe.edu.upc.center.platform.profiles" )
public class ProfileExceptionHandler {

  @ExceptionHandler(DateTimeParseException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<BadRequestResponse> handleDateTimeParseException(
      DateTimeParseException ex) {
    Map<String, String> fieldErrors = new LinkedHashMap<>();
    fieldErrors.put("birthDate", "Invalid date format (YYYY-MM-DD): " + ex.getMessage());

    var response = new BadRequestResponse(
        HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
        "Date parsing failed", fieldErrors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<BadRequestResponse> handleJsonParseError(
      HttpMessageNotReadableException ex) {
    Throwable cause = ex.getCause();

    if (cause instanceof InvalidFormatException) {
      InvalidFormatException ife = (InvalidFormatException) cause;
      Map<String, String> fieldErrors = new LinkedHashMap<>();
      fieldErrors.put("birthDate", "Invalid value '" + ife.getValue().toString()
          + "' for LocalDate. Expected format (YYYY-MM-DD) ");

      var response = new BadRequestResponse(
          HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
          "Date parsing failed", fieldErrors);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
  }

}
