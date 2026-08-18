package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;

public record CourseResponse(UUID id, String ref, String title, int credits) {}
