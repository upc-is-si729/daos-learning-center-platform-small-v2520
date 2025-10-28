package pe.edu.upc.center.platform.faculties.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Represents the unique identifier for a Program entity.
 *
 * <p>This class is an embeddable value object that encapsulates the programId attribute.
 * It ensures that the programId is non-negative and provides a default constructor
 * that initializes the programId to 0.</p>
 *
 * @param programId the unique identifier for the program
 */
@Embeddable
public record ProgramId(Long programId) {

  /**
   * Constructs a ProgramId object with the specified programId.
   *
   * @param programId the unique identifier for the program
   * @throws IllegalArgumentException if programId is negative
   */
  public ProgramId {
    if (Objects.isNull(programId) || programId < 0) {
      throw new IllegalArgumentException("ProgramId cannot be null or negative");
    }
  }

  /**
   * Constructs a ProgramId object with a default value of 0.
   */
  public ProgramId() {
    this(0L);
  }
}
