package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import pe.edu.upc.center.platform.shared.utils.Util;

import java.util.Objects;

/**
 * Value object representing a document.
 */
@Embeddable
public record Document(DocumentTypes type, String number) {

  public Document {
    if (Objects.isNull(type) || type.toString().isBlank()) {
      throw new IllegalArgumentException("[Document] Document type cannot be null or blank");
    }
    if (Objects.isNull(number) || number.isBlank()) {
      throw new IllegalArgumentException("[Document] Document number cannot be null or blank");
    }
    if (type != DocumentTypes.OTRO && type != DocumentTypes.DNI
        && type != DocumentTypes.CARNET_EXTRANJERIA && type != DocumentTypes.RUC
        && type != DocumentTypes.PASAPORTE && type != DocumentTypes.PTP) {
      throw new IllegalArgumentException("[Document] Invalid document type: [" + type + "]");
    }
    if (type == DocumentTypes.DNI && number.length() != Util.DNI_LENGTH) {
      throw new IllegalArgumentException(
          String.format("[Document] DNI must have exactly %d digits", Util.DNI_LENGTH));
    }
    if (type == DocumentTypes.CARNET_EXTRANJERIA && number.length()
        != Util.CARNET_EXTRANJERIA_LENGTH) {
      throw new IllegalArgumentException(
          String.format("[Document] Carnet Extranjero must have exactly %d digits",
              Util.CARNET_EXTRANJERIA_LENGTH));
    }
    if (type == DocumentTypes.RUC && number.length() != Util.RUC_LENGTH) {
      throw new IllegalArgumentException(
          String.format("[Document] RUC must have exactly %d digits", Util.RUC_LENGTH));
    }
    if (type == DocumentTypes.PASAPORTE && number.length() != Util.PASAPORTE_LENGTH) {
      throw new IllegalArgumentException(
          String.format("[Document] Pasaporte must have exactly %s digits",
              Util.PASAPORTE_LENGTH));
    }
    if (type == DocumentTypes.PTP && number.length() != Util.PTP_LENGTH) {
      throw new IllegalArgumentException(
          String.format("[Document] PTP must have exactly %s digits", Util.PTP_LENGTH));
    }
  }
  // Default constructor for JPA.
  public Document() {
    this(null, null);
  }

  public String getFullDocument() {
    return String.format("%s %s", type.toString(), number);
  }

}
