package school.hei.graduates.entity;

import jakarta.persistence.*;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String ref;

  private String firstName;

  private String lastName;

  private String email;

  @ManyToOne
  @JoinColumn(name = "promotion_id")
  private Promotion promotion;
}
