package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ChangeStudentGroup(@NotNull UUID groupId) {}
