package school.hei.graduates.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.TeachingAssignment;

@Repository
public interface TeachingAssignmentRepository
        extends JpaRepository<TeachingAssignment, UUID> {

    List<TeachingAssignment> findByTeacher_Id(UUID teacherId);

    List<TeachingAssignment> findByCourse_Id(UUID courseId);

    List<TeachingAssignment> findByGroup_Id(UUID groupId);

    boolean existsByTeacher_IdAndCourse_Id(
            UUID teacherId,
            UUID courseId);

    boolean existsByTeacher_IdAndCourse_IdAndGroup_Id(
            UUID teacherId,
            UUID courseId,
            UUID groupId);
}