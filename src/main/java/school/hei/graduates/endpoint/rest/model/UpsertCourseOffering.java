package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import school.hei.graduates.entity.Semester;

public record UpsertCourseOffering(
    UUID id,
    @NotNull UUID courseId,
    @NotNull UUID groupId,
    @NotNull Semester semester,
    @NotBlank String academicYear) {}
