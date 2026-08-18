package school.hei.graduates.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

  Optional<Student> findByRef(String ref);

  Optional<Student> findByEmail(String email);

  List<Student> findByPromotion_Id(UUID promotionId);
}
