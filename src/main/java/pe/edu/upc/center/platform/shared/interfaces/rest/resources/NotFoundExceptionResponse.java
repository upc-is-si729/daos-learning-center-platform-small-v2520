package pe.edu.upc.center.platform.shared.interfaces.rest.resources;

public record NotFoundExceptionResponse(
    int status, String error, String message
) {
}
