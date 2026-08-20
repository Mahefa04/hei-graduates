package school.hei.graduates.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

  Optional<Teacher> findByRef(String ref);

  Optional<Teacher> findByEmail(String email);
}