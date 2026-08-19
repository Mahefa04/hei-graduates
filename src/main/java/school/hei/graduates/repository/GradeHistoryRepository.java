package school.hei.graduates.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.GradeHistory;

@Repository
public interface GradeHistoryRepository extends JpaRepository<GradeHistory, UUID> {

  List<GradeHistory> findByGrade_IdOrderByModifiedAtAsc(UUID gradeId);
}
