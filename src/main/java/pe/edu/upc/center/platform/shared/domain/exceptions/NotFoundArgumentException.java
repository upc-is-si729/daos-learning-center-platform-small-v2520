package pe.edu.upc.center.platform.shared.domain.exceptions;

public class NotFoundArgumentException extends RuntimeException {

  public NotFoundArgumentException(String message) {
    super(message);
  }

}