package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;
import school.hei.graduates.entity.Role;

public record UserResponse(UUID id, String email, Role role, UUID studentId, UUID teacherId) {}
