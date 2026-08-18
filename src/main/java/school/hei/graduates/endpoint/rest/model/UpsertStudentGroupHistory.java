package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record UpsertStudentGroupHistory(
        UUID id,
        @NotNull UUID studentId,
        @NotNull UUID groupId,
        @NotNull LocalDate startDate,
        LocalDate endDate) {
}