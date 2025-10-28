package pe.edu.upc.center.platform.faculties.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record CurriculumId(Long curriculumId) {

  public CurriculumId {
    if (Objects.isNull(curriculumId) || curriculumId < 0) {
      throw new IllegalArgumentException("Curriculum ID cannot be null or negative");
    }
  }
  public CurriculumId() {
    this(0L);
  }
}
