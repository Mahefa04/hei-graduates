package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;

public record StudentResponse(
    UUID id,
    String ref,
    String firstName,
    String lastName,
    String email,
    PromotionResponse promotion) {}
