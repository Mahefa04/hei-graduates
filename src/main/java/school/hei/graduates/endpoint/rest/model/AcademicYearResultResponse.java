package school.hei.graduates.endpoint.rest.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AcademicYearResultResponse(
        UUID studentId,
        int year,
        BigDecimal average,
        int credits,
        boolean complete,
        List<CourseResultResponse> courses) {
}