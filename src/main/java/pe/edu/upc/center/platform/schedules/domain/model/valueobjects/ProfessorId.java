package pe.edu.upc.center.platform.schedules.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record ProfessorId(Long professorId) {
  public ProfessorId {
    if (Objects.isNull(professorId) || professorId <= 0) {
      throw new IllegalArgumentException("Professor ID cannot be null or negative");
    }
  }
  public ProfessorId() {
    this(0L);
  }
}
