package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;

public record TeachingAssignmentResponse(
    UUID id, CourseOfferingResponse courseOffering, TeacherResponse teacher) {}
