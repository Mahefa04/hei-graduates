package school.hei.graduates.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Optional<Group> findByRef(String ref);
}