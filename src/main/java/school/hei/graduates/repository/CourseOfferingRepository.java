package school.hei.graduates.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Semester;

@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, UUID> {

  List<CourseOffering> findByCourse_Id(UUID courseId);

  List<CourseOffering> findByGroup_Id(UUID groupId);

  List<CourseOffering> findBySemester(Semester semester);

  List<CourseOffering> findByAcademicYear(String academicYear);

  List<CourseOffering> findByGroup_IdAndSemester(UUID groupId, Semester semester);

  List<CourseOffering> findByAcademicYearAndSemester(String academicYear, Semester semester);
}
