package pe.edu.upc.center.platform.schedules.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.platform.schedules.domain.model.valueobjects.CourseId;
import pe.edu.upc.center.platform.schedules.domain.model.valueobjects.ProfessorId;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "course_assigns")
public class CourseAssign extends AuditableAbstractAggregateRoot<CourseAssign> {

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "courseId",
          column = @Column(name = "course_id", nullable = false))
  })
  private CourseId courseId;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "professorId",
          column = @Column(name = "professor_id", nullable = false))
  })
  private ProfessorId professorId;

  @Column(name = "nrc", length = 5, nullable = false)
  private String nrc;

  @Column(name = "period", length = 6, nullable = false)
  private String period;

  @OneToMany(mappedBy = "courseAssign", cascade = CascadeType.ALL)
  private List<ClassSchedule> classSchedules;

  public CourseAssign() {
    this.classSchedules = new ArrayList<>();
  }
}
