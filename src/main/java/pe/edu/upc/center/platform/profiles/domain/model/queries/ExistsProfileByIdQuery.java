package pe.edu.upc.center.platform.profiles.domain.model.queries;

/**
 * This class represents the query to check if a profile exists by its id.
 *
 * @param profileId - the id of the profile.
 */
public record ExistsProfileByIdQuery(Long profileId) {
}
