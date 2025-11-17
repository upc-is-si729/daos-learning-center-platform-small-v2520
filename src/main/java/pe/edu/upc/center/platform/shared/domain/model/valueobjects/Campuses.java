package pe.edu.upc.center.platform.shared.domain.model.valueobjects;

public enum Campuses {
  SAN_MIGUEL ("C"),
  SAN_ISIDRO ("I"),
  VILLA ("V"),
  MONTERRICO ("M");

  private final String value;
  Campuses(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }
}
