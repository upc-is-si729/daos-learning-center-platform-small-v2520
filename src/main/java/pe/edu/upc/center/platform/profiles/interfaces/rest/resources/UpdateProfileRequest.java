package pe.edu.upc.center.platform.profiles.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

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
public record UpdateProfileRequest(

    @JsonProperty("id")
    @NotNull(message = "id must not be null")
    Long id,

    @JsonProperty("firstName")
    @NotNull @NotBlank
    String firstName,

    @JsonProperty("lastName")
    @NotNull(message = "lastName must not be null")
    @NotBlank(message = "lastName must not be blank")
    String lastName,

    @JsonProperty("documentType")
    @Min(value = 0, message = "documentType must be a valid DocumentTypes ordinal")
    @Max(value = 15, message = "documentType must be a valid DocumentTypes ordinal")
    int documentType,

    @JsonProperty("documentNumber")
    @NotNull(message = "documentNumber must not be null")
    @NotBlank(message = "documentNumber must not be blank")
    String documentNumber,

    @JsonProperty("birthDate")
    @NotNull(message = "birthDate must not be null")
    @Past(message = "birthDate must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate,

    @JsonProperty("email")
    @Email(message = "email must be a valid email address")
    @NotBlank(message = "email must not be blank")
    String email,

    @JsonProperty("street")
    @NotNull(message = "street must not be null")
    @NotBlank(message = "street must not be blank")
    String street,

    @JsonProperty("streetNumber")
    @NotNull(message = "streetNumber must not be null")
    @NotBlank(message = "streetNumber must not be blank")
    String streetNumber,

    @JsonProperty("city")
    @NotNull(message = "city must not be null")
    @NotBlank(message = "city must not be blank")
    String city,

    @JsonProperty("postalCode")
    @NotNull(message = "postalCode must not be null")
    @NotBlank(message = "postalCode must not be blank")
    @Pattern(regexp = "\\d{4,6}", message = "postalCode must be numeric and between 4 and 6 digits")
    String postalCode,

    @JsonProperty("country")
    @NotNull(message = "country must not be null")
    @NotBlank(message = "country must not be blank")
    String country
                                   ) {
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
