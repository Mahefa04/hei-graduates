package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;

public record PromotionResponse(UUID id, String ref, int startYear) {}
