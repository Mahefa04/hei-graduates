package school.hei.graduates.endpoint.rest.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record UpsertTeacher(
    UUID id,
    @NotBlank String ref,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank @Email String email) {}
