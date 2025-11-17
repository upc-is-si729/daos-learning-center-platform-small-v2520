package pe.edu.upc.center.platform.shared.domain.model.valueobjects;

public enum DayOfWeeks {
  LUNES (1),
  MARTES (2),
  MIERCOLES (3),
  JUEVES (4),
  VIERNES (5),
  SABADO (6),
  DOMINGO (7);

  private final int value;

  DayOfWeeks(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

}
