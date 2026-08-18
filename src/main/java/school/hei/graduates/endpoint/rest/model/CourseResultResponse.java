package school.hei.graduates.endpoint.rest.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CourseResultResponse(
    UUID studentId, CourseOfferingResponse courseOffering, BigDecimal average, boolean complete) {}
