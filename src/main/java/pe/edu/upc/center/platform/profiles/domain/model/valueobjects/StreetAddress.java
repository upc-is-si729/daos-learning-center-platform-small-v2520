package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Value object representing a street address.
 */
@Embeddable
public record StreetAddress(@NotNull @NotBlank String street, @NotNull @NotBlank String number,
                            @NotNull @NotBlank String city, @NotNull @NotBlank String postalCode,
                            @NotNull @NotBlank String country) {

  /**
   * Default constructor for JPA.
   */
  public StreetAddress() {
    this(null, null, null, null, null);
  }

  /**
   * Constructs a StreetAddress instance with the specified details.
   *
   * @param street the street name
   * @param number the street number
   * @param city the city name
   * @param postalCode the postal code
   * @param country the country name
   */
  public StreetAddress {
    if (Objects.isNull(street) || street.isBlank()) {
      throw new IllegalArgumentException("Street cannot be null or blank");
    }
    if (Objects.isNull(number) || number.isBlank()) {
      throw new IllegalArgumentException("Street Number cannot be null or blank");
    }
    if (number.length() > 5) {
      throw new IllegalArgumentException("Street Number cannot have more than 5 digits");
    }
    if (Objects.isNull(city) || city.isBlank()) {
      throw new IllegalArgumentException("City cannot be null or blank");
    }
    if (Objects.isNull(postalCode) || postalCode.isBlank()) {
      throw new IllegalArgumentException("Postal code cannot be null or blank");
    }
    if (postalCode.length() != 5) {
      throw new IllegalArgumentException("Postal code must be 5 digits long");
    }
    if(Objects.isNull(country) || country.isBlank()){
      throw new IllegalArgumentException("Country cannot be null or blank");
    }
  }
  public String getFullAddress() {
    return String.format("%s %s, %s, %s, %s", street, number, city, postalCode, country);
  }
}
