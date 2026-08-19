package school.hei.graduates.endpoint.rest.model;

import java.math.BigDecimal;
import java.util.UUID;

public record GradeResponse(
    UUID id, StudentResponse student, ExamResponse exam, BigDecimal value) {}
