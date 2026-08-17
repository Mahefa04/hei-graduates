package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import school.hei.graduates.entity.Role;

public record CreateUser(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotNull Role role,
        UUID studentId,
        UUID teacherId) {}