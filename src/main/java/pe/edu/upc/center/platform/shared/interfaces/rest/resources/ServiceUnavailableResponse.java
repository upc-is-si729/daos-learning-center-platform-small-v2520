package pe.edu.upc.center.platform.shared.interfaces.rest.resources;

public record ServiceUnavailableResponse(
    int status, String error, String message
) {
}
