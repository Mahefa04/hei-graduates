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

    List<TeachingAssignment> findByCourseOffering_Id(
            UUID courseOfferingId);

    boolean existsByTeacher_IdAndCourseOffering_Id(
            UUID teacherId,
            UUID courseOfferingId);
}