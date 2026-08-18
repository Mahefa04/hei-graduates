package school.hei.graduates.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.graduates.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, UUID> {

  Optional<Promotion> findByRef(String ref);

  Optional<Promotion> findByStartYear(int startYear);
}
