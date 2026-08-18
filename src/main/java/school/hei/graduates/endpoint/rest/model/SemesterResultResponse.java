package school.hei.graduates.endpoint.rest.model;

import java.math.BigDecimal;
import java.util.List;

public record SemesterResultResponse(
    String academicYear,
    String semester,
    BigDecimal average,
    int credits,
    boolean complete,
    List<CourseResultResponse> courses) {}
