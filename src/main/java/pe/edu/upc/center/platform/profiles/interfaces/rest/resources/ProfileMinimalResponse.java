package pe.edu.upc.center.platform.profiles.interfaces.rest.resources;

/**
 * Response DTO for minimal profile information.
 *
 * @param id        the profile ID
 * @param fullName  the full name of the profile
 * @param document  the document identifier
 * @param birthDate the birthdate of the profile
 * @param email     the email address
 * @param address   the physical address
 */

public record ProfileMinimalResponse(Long id, String fullName, String document,
                                     String birthDate, String email, String address) {
}
