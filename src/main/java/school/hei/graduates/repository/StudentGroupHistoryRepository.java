package school.hei.graduates.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.StudentGroupHistory;

@Repository
public interface StudentGroupHistoryRepository
        extends JpaRepository<StudentGroupHistory, UUID> {

    List<StudentGroupHistory> findByStudent_IdOrderByStartDateAsc(
            UUID studentId
    );

    Optional<StudentGroupHistory> findFirstByStudent_IdAndEndDateIsNull(
            UUID studentId
    );
}