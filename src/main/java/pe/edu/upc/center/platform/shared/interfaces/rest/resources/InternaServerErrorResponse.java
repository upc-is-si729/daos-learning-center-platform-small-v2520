package pe.edu.upc.center.platform.shared.interfaces.rest.resources;

public record InternaServerErrorResponse(
    int status, String error, String message
) {
}
