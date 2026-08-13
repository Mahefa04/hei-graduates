package school.hei.graduates.endpoint.rest.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ExamResponse(
        UUID id,
        String title,
        Instant examDate,
        BigDecimal coefficient,
        CourseResponse course) {}