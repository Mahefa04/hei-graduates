package school.hei.graduates.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

  Optional<Course> findByRef(String ref);
}
