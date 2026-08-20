package school.hei.graduates.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import school.hei.graduates.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, UUID> {

  List<Grade> findByStudent_Id(UUID studentId);

  List<Grade> findByExam_Id(UUID examId);

  List<Grade> findByStudent_IdAndExam_CourseOffering_Id(UUID studentId, UUID courseOfferingId);

  Optional<Grade> findByStudent_IdAndExam_Id(UUID studentId, UUID examId);

  List<Grade> findByExam_CourseOffering_Id(UUID courseOfferingId);
}
