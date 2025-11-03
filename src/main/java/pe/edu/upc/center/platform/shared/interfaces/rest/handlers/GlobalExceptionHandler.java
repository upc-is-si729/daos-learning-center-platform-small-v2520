package pe.edu.upc.center.platform.shared.interfaces.rest.handlers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.IllegalArgumentExceptionResponse;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.NullPointerExceptionResponse;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.PersistenceExceptionResponse;
import pe.edu.upc.center.platform.shared.interfaces.rest.resources.ValidationExceptionResponse;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ValidationExceptionResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new LinkedHashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

    var response = new ValidationExceptionResponse(
        HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
        "Validation failed", errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<IllegalArgumentExceptionResponse> handleIllegalArgumentException(
      IllegalArgumentException ex) {

    var response = new IllegalArgumentExceptionResponse(
        HttpStatus.BAD_REQUEST.value(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(PersistenceException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public ResponseEntity<PersistenceExceptionResponse> handlePersistenceException(
      PersistenceException ex) {

    var response = new PersistenceExceptionResponse(
        HttpStatus.SERVICE_UNAVAILABLE.value(),
        HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
  }

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<NullPointerExceptionResponse> handleNullPointerException(
      NullPointerException ex) {

    var response = new NullPointerExceptionResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

}


