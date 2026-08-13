package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpsertTeachingAssignment(
        UUID id,
        @NotNull UUID courseId,
        @NotNull UUID teacherId,
        @NotNull UUID groupId) {}