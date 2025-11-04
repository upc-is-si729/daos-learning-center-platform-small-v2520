package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import pe.edu.upc.center.platform.shared.utils.Util;

/**
 * Value object representing a street address.
 */
@Embeddable
public record StreetAddress(String street, String number, String city, String postalCode,
                            String country) {
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
      throw new IllegalArgumentException("[StreetAddress] Street cannot be null or blank");
    }
    if (Objects.isNull(number) || number.isBlank()) {
      throw new IllegalArgumentException("[StreetAddress] Street Number cannot be null or blank");
    }
    if (number.length() > Util.STREET_NUMBER_MAX_LENGTH) {
      throw new IllegalArgumentException(
          String.format("[StreetAddress] Street Number cannot have more than %s digits",
              Util.STREET_NUMBER_MAX_LENGTH));
    }
    if (Objects.isNull(city) || city.isBlank()) {
      throw new IllegalArgumentException("[StreetAddress] City cannot be null or blank");
    }
    if (Objects.isNull(postalCode) || postalCode.isBlank()) {
      throw new IllegalArgumentException("[StreetAddress] Postal code cannot be null or blank");
    }
    if (postalCode.length() != Util.POSTAL_CODE_LENGTH) {
      throw new IllegalArgumentException(
          String.format("[StreetAddress] Postal code must be %s digits long",
              Util.POSTAL_CODE_LENGTH));
    }
    if(Objects.isNull(country) || country.isBlank()){
      throw new IllegalArgumentException("[StreetAddress] Country cannot be null or blank");
    }
  }

  /**
   * Default constructor for JPA.
   */
  public StreetAddress() {
    this(null, null, null, null, null);
  }

  public String getFullAddress() {
    return String.format("%s %s, %s, %s, %s", street, number, city, postalCode, country);
  }
}
