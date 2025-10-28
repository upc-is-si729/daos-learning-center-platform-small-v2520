package pe.edu.upc.center.platform.profiles.domain.model.valueobjects;

import java.util.Arrays;

public enum DocumentTypes {
  OTRO(0),
  DNI(1),
  CARNET_EXTRANJERIA(4),
  RUC(6),
  PASAPORTE(7),
  PTP(15);

  private final int value;

  DocumentTypes(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public static DocumentTypes fromValue(int value) {
    return Arrays.stream(DocumentTypes.values())
        .filter(dt -> dt.value == value)
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException("Invalid value for DocumentType: " + value));
  }
}
