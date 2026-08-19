package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record UpsertPromotion(UUID id, @NotBlank String ref, @Min(2000) int startYear) {}
