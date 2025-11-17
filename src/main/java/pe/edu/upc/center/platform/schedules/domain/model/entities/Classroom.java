package pe.edu.upc.center.platform.schedules.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.platform.shared.domain.model.valueobjects.Campuses;
import pe.edu.upc.center.platform.schedules.domain.model.valueobjects.TypeClassrooms;
import pe.edu.upc.center.platform.shared.domain.model.entities.AuditableModel;

@Getter
@Entity
@Table(name = "classrooms")
public class Classroom extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 10, nullable = false)
  private String name;

  @Column(name = "capacity", nullable = false)
  private int capacity;

  @Column(name = "seats", nullable = false)
  private int seats;

  @Column(name = "type", nullable = false)
  private TypeClassrooms type;

  @Column(name = "campus", nullable = false)
  private Campuses campus;

  public Classroom() {}

}
