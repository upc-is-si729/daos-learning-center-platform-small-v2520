package pe.edu.upc.center.platform.shared.interfaces.rest.resources;

public record PersistenceExceptionResponse(
    int status, String error, String message
) {
}
