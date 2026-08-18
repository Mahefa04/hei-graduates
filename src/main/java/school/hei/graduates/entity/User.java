package school.hei.graduates.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @OneToOne
  @JoinColumn(name = "student_id", unique = true)
  private Student student;

  @OneToOne
  @JoinColumn(name = "teacher_id", unique = true)
  private Teacher teacher;
}
