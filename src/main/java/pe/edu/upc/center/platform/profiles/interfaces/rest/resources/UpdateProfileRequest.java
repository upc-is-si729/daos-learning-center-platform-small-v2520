package pe.edu.upc.center.platform.profiles.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Request to create a profile.
 *
 * @param id The ID of the profile to update.
 * @param firstName The first name of the profile.
 * @param lastName The last name of the profile.
 * @param documentType The type of document of the profile.
 * @param documentNumber The number of documents of the profile.
 * @param birthDate The birthdate of the profile.
 * @param email The email of the profile.
 * @param street The street address of the profile.
 * @param streetNumber The number the street address of the profile.
 * @param city The city street address of the profile.
 * @param postalCode The postal code the street address of the profile.
 * @param country The country the street address of the profile.
 */
public record UpdateProfileRequest(Long id, String firstName, String lastName,
                                   int documentType, String documentNumber,
                                   LocalDate birthDate,
                                   String email, String street, String streetNumber,
                                   String city, String postalCode, String country) {
  @JsonCreator
  public UpdateProfileRequest(@JsonProperty("id") Long id,
                              @JsonProperty("firstName") String firstName,
                              @JsonProperty("lastName") String lastName,
                              @JsonProperty("documentType") int documentType,
                              @JsonProperty("documentNumber") String documentNumber,
                              @JsonProperty("birthDate") LocalDate birthDate,
                              @JsonProperty("email") String email,
                              @JsonProperty("street") String street,
                              @JsonProperty("streetNumber") String streetNumber,
                              @JsonProperty("city") String city,
                              @JsonProperty("postalCode") String postalCode,
                              @JsonProperty("country") String country) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.documentType = documentType;
    this.documentNumber = documentNumber;
    this.birthDate = birthDate;
    this.email = email;
    this.street = street;
    this.streetNumber = streetNumber;
    this.city = city;
    this.postalCode = postalCode;
    this.country = country;
  }
}
