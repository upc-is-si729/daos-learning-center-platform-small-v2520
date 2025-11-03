package pe.edu.upc.center.platform.shared.interfaces.rest.resources;

public record NullPointerExceptionResponse(
    int status, String error, String message
) {
}
