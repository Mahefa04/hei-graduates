package school.hei.graduates.endpoint.rest.model;

import java.time.LocalDate;
import java.util.UUID;

public record StudentGroupHistoryResponse(
        UUID id,
        StudentResponse student,
        GroupResponse group,
        LocalDate startDate,
        LocalDate endDate) {
}