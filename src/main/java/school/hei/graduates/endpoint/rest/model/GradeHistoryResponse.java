package school.hei.graduates.endpoint.rest.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record GradeHistoryResponse(
        UUID id,
        UUID gradeId,
        BigDecimal oldValue,
        BigDecimal newValue,
        String reason,
        String modifiedBy,
        Instant modifiedAt) {}