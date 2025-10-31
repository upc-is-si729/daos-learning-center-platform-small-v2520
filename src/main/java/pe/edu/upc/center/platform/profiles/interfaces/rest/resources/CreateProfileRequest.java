package pe.edu.upc.center.platform.profiles.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.DocumentTypes;

import java.time.LocalDate;

/**
 * Request to create a profile.
 *
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
public record CreateProfileRequest(
    @JsonProperty("firstName")
    @NotNull @NotBlank
    String firstName,

    @JsonProperty("lastName")
    @NotNull @NotBlank
    String lastName,

    @JsonProperty("documentType")
    @Min(0) @Max(15)
    int documentType,

    @JsonProperty("documentNumber")
    @NotNull @NotBlank @Size(min = 8, max = 12)
    String documentNumber,

    @JsonProperty("birthDate") @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull @Past
    LocalDate birthDate,

    @JsonProperty("email")
    @NotNull @NotBlank @Email
    String email,

    @JsonProperty("street")
    @NotNull @NotBlank
    String street,

    @JsonProperty("streetNumber")
    @NotNull @NotBlank
    @Size(min = 1, max = 5)
    String streetNumber,

    @JsonProperty("city")
    @NotNull @NotBlank
    String city,

    @JsonProperty("postalCode")
    @NotNull @NotBlank
    @Pattern(regexp = "\\d{5}", message = "Postal code must be 5 digits")
    @Size(min = 5, max = 5)
    String postalCode,

    @JsonProperty("country")
    @NotNull @NotBlank
    String country) {

  /*@JsonCreator
  public CreateProfileRequest(@JsonProperty("firstName") String firstName,
                              @JsonProperty("lastName") String lastName,
                              @JsonProperty("documentType") int documentType,
                              @JsonProperty("documentNumber") String documentNumber,
                              @JsonProperty("birthDate") LocalDate birthDate,
                              @JsonProperty("email") String email,
                              @JsonProperty("street") String street,
                              @JsonProperty("number") String streetNumber,
                              @JsonProperty("city") String city,
                              @JsonProperty("postalCode") String postalCode,
                              @JsonProperty("country") String country) {
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
  }*/

}

