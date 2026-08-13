package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record UpsertGroup(
        UUID id,
        @NotBlank String ref) {}