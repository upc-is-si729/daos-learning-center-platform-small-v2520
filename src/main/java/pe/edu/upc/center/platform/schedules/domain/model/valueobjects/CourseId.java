package pe.edu.upc.center.platform.schedules.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record CourseId(Long courseId) {
  public CourseId {
    if (Objects.isNull(courseId) || courseId <= 0) {
      throw new IllegalArgumentException("Course ID cannot be null or negative");
    }
  }
  public CourseId() {
    this(0L);
  }
}
