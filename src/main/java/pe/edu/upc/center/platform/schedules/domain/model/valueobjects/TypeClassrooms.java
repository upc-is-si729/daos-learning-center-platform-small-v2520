package pe.edu.upc.center.platform.schedules.domain.model.valueobjects;

public enum TypeClassrooms {
  TEORIA ("TE"),
  LABORATORIO_COMPUTO ("LC"),
  TALLER_ARQUITECTURA ("TA"),
  TALLER_DIBUJO ("TD"),
  SALA_REUNIONES ("SR"),
  LABORATORIO_MAC ("LM"),
  AULA_HIBRIDA ("AH");

  private final String value;
  TypeClassrooms(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }

}
