package pe.edu.upc.center.platform.shared.interfaces.rest.resources;

import java.util.Map;

public record ValidationExceptionResponse(
    int status, String error, String message, Map<String, String> fieldErrors
) {
}
