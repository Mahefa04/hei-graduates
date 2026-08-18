package school.hei.graduates.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.Grade;
import school.hei.graduates.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

  Optional<Teacher> findByRef(String ref);

  Optional<Teacher> findByEmail(String email);

  @Repository
  interface GradeRepository extends JpaRepository<Grade, UUID> {

    List<Grade> findByStudent_Id(UUID studentId);

    List<Grade> findByExam_Id(UUID examId);

    Optional<Grade> findByStudent_IdAndExam_Id(UUID studentId, UUID examId);

    List<Grade> findByExam_CourseOffering_Id(UUID courseOfferingId);

    List<Grade> findByStudent_IdAndExam_CourseOffering_Id(UUID studentId, UUID courseOfferingId);
  }
}
