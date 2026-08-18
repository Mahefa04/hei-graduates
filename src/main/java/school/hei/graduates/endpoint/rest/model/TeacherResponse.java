package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;

public record TeacherResponse(
    UUID id, String ref, String firstName, String lastName, String email) {}
