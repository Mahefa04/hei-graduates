package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;

public record TeachingAssignmentResponse(
        UUID id,
        CourseResponse course,
        TeacherResponse teacher,
        GroupResponse group) {}