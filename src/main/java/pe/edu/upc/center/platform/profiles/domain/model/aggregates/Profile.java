package pe.edu.upc.center.platform.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.valueobjects.*;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;

/**
 * Represents a user profile in the system.
 */
@Entity
@Table(name = "profiles")
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

  @Getter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "firstName",
          column = @Column(name = "first_name", length = 50, nullable = false)),
      @AttributeOverride(name = "lastName",
          column = @Column(name = "last_name", length = 50, nullable = false))
  })
  private PersonName name;

  @Getter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "type",
          column = @Column(name = "document_type", nullable = false)),
      @AttributeOverride(name = "number",
          column = @Column(name = "document_number", length = 15, nullable = false))
  })
  private Document document;

  @Getter
  @Past
  @Column(name = "birth_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate birthDate;

  @Getter
  @Min(0)
  @Max(100)
  @Column(name = "age", columnDefinition = "smallint", nullable = false)
  private int age;

  @Getter
  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "address", column = @Column(name = "email_address", length = 100, nullable = false))})
  private EmailAddress email;

  @Getter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "street",
          column = @Column(name = "address_street", length = 100, nullable = false)),
      @AttributeOverride(name = "number",
          column = @Column(name = "address_number", length = 5, nullable = false)),
      @AttributeOverride(name = "city",
          column = @Column(name = "address_city", length = 20, nullable = false)),
      @AttributeOverride(name = "postalCode",
          column = @Column(name = "address_postal_code", length = 5, nullable = false)),
      @AttributeOverride(name = "country",
          column = @Column(name = "address_country", length = 20, nullable = false))
  })
  private StreetAddress address;

  /**
   * Default constructor for JPA.
   */
  public Profile() {
  }

  /**
   * Constructs a Profile instance from a CreateProfileCommand.
   *
   * @param command createProfileCommand containing profile details
   */
  public Profile(CreateProfileCommand command) {
    this.name = command.name();
    this.document = command.document();
    this.birthDate = command.birthDate();
    this.age = command.age();
    this.email = command.email();
    this.address = command.address();
  }
  /** Update the profile with the specified details.
   *
   * @param command the UpdateProfileCommand containing the new profile details
   */
  public void updateProfile(UpdateProfileCommand command) {
    this.name = command.name();
    this.document = command.document();
    this.birthDate = command.birthDate();
    this.age = command.age();
    this.email = command.email();
    this.address = command.address();
  }

  // Evaluar si se requieren
  /** Updates the street address of the profile.
   *
   * @param street the new street address
   */
  public void updateStreetAddress(String street, String number, String city,
                                  String postalCode, String country) {
    this.address = new StreetAddress(street, number, city, postalCode, country);
  }
  public void updateName(String firstName, String lastName) {
    this.name = new PersonName(firstName, lastName);
  }

  public void updateEmail(String email) {
    this.email = new EmailAddress(email);
  }

  public void updateAddress(String street, String number, String city, String postalCode, String country) {
    this.address = new StreetAddress(street, number, city, postalCode, country);
  }

  public String getFullName() {
    return name.getFullName();
  }

  public String getFullDocument() {
    return document.getFullDocument();
  }

  public String getFullAddress() {
    return address.getFullAddress();
  }

}
