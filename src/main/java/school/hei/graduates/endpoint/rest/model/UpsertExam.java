package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record UpsertExam(
        UUID id,
        @NotBlank String title,
        @NotNull Instant examDate,
        @NotNull @DecimalMin("0.0") @DecimalMax("1.0") BigDecimal coefficient,
        @NotNull UUID courseOfferingId) {}