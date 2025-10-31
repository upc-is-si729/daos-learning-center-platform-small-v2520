package pe.edu.upc.center.platform.shared.interfaces.rest.resources;

public record IllegalArgumentExceptionResponse(
    int status, String error, String message
  ) {
}
