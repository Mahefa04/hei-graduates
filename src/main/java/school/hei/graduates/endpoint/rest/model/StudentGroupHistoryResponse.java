package school.hei.graduates.endpoint.rest.model;

import java.time.Instant;
import java.util.UUID;

public record StudentGroupHistoryResponse(
        UUID id,
        UUID studentId,
        GroupResponse group,
        Instant startDate,
        Instant endDate) {}