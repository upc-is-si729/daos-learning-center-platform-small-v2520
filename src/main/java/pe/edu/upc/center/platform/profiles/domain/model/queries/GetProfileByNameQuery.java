package pe.edu.upc.center.platform.profiles.domain.model.queries;

import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.PersonName;

import java.util.Objects;

/**
 * Query to get a profile by last name.
 *
 * @param name the person's name
 *
 */
public record GetProfileByNameQuery(PersonName name) {

  public GetProfileByNameQuery {
    Objects.requireNonNull(name, "name must not be null");
  }
}
