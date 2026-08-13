package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record UpsertCourse(
        UUID id,
        @NotBlank String ref,
        @NotBlank String title,
        @Min(1) int credits) {}