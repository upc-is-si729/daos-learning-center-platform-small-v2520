package pe.edu.upc.center.platform.schedules.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.platform.schedules.domain.model.entities.Classroom;
import pe.edu.upc.center.platform.shared.domain.model.valueobjects.DayOfWeeks;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


@Getter
@Entity
@Table(name = "class_schedules")
public class ClassSchedule extends AuditableAbstractAggregateRoot<ClassSchedule> {

  @ManyToOne
  @JoinColumn(name = "classroom_id")
  private Classroom classroom;

  @ManyToOne
  @JoinColumn(name = "course_assign_id")
  private CourseAssign courseAssign;

  @Column(name = "day_of_week", nullable = false)
  private DayOfWeeks dayOfWeek;

  @Column(name = "start_time", length = 5, nullable = false)
  private String startTime;

  @Column(name = "end_time", length = 5, nullable = false)
  private String endTime;

  public ClassSchedule() {}


}
