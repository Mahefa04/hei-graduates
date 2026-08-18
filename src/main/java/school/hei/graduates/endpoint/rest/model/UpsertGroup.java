package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import school.hei.graduates.entity.Track;

public record UpsertGroup(UUID id, @NotBlank String ref, @NotNull Track track) {}
