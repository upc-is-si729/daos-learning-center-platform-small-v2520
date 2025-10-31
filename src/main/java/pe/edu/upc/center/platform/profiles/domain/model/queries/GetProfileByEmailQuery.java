package pe.edu.upc.center.platform.profiles.domain.model.queries;

import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.EmailAddress;

import java.util.Objects;

public record GetProfileByEmailQuery(EmailAddress email) {

  public GetProfileByEmailQuery {
    Objects.requireNonNull(email, "Email cannot be null");
  }

}
