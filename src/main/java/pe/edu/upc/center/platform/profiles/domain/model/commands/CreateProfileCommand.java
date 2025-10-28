package pe.edu.upc.center.platform.profiles.domain.model.commands;

import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.DocumentTypes;

import java.time.LocalDate;

/**
 * Command to create a new profile.
 *
 * @param firstName The first name of the profile.
 * @param lastName The last name of the profile.
 * @param documentType The type of document of the profile.
 * @param documentNumber The number of documents of the profile.
 * @param birthDate The birthdate of the profile.
 * @param age The age of the profile.
 * @param email The email of the profile.
 * @param street The street address of the profile.
 * @param streetNumber The number the street address of the profile.
 * @param city The city street address of the profile.
 * @param postalCode The postal code the street address of the profile.
 * @param country The country the street address of the profile.
 */
public record CreateProfileCommand(String firstName, String lastName,
                                   DocumentTypes documentType, String documentNumber,
                                   LocalDate birthDate, int age,
                                   String email, String street, String streetNumber,
                                   String city, String postalCode, String country) {
}
