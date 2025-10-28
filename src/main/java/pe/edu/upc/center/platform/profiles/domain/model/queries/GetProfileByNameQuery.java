package pe.edu.upc.center.platform.profiles.domain.model.queries;

/**
 * Query to get a profile by last name.
 *
 * @param firstName The first name of the profile.
 * @param lastName The last name of the profile.
 *
 */
public record GetProfileByNameQuery(String firstName, String lastName) {
}
