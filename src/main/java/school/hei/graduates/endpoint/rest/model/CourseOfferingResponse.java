package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;
import school.hei.graduates.entity.Semester;

public record CourseOfferingResponse(
        UUID id,
        CourseResponse course,
        GroupResponse group,
        Semester semester,
        String academicYear) {}