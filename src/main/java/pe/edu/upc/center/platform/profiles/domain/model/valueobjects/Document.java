package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/** Value object representing a document.
 *
 * */
@Embeddable
public record Document(@NotNull @NotBlank DocumentTypes type,
                       @NotNull @NotBlank String number) {
  public Document {
    if (Objects.isNull(type) || type.toString().isBlank()) {
      throw new IllegalArgumentException("Document type cannot be null or blank");
    }
    if (Objects.isNull(number) || number.isBlank()) {
      throw new IllegalArgumentException("Document number cannot be null or blank");
    }
    if (type != DocumentTypes.OTRO && type != DocumentTypes.DNI
        && type != DocumentTypes.CARNET_EXTRANJERIA && type != DocumentTypes.RUC
        && type != DocumentTypes.PASAPORTE && type != DocumentTypes.PTP) {
      throw new IllegalArgumentException("Invalid document type: [" + type + "]");
    }
    if (type == DocumentTypes.DNI && number.length() != 8) {
      throw new IllegalArgumentException("DNI must have exactly 8 digits");
    }
    if (type == DocumentTypes.CARNET_EXTRANJERIA && number.length() != 12) {
      throw new IllegalArgumentException("Carnet Extranjero must have exactly 12 digits");
    }
    if (type == DocumentTypes.RUC && number.length() != 11) {
      throw new IllegalArgumentException("RUC must have exactly 11 digits");
    }
    if (type == DocumentTypes.PASAPORTE && number.length() != 10) {
      throw new IllegalArgumentException("Pasaporte must have exactly 10 digits");
    }
    if (type == DocumentTypes.PTP && number.length() != 12) {
      throw new IllegalArgumentException("PTP must have exactly 12 digits");
    }
  }
  // Default constructor for JPA.
  public Document() {
    this(null, null);
  }

  public String getFullDocument() {
    return String.format("%s: %s", type, number);
  }

}
