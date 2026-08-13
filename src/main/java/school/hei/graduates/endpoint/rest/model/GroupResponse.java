package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;

public record GroupResponse(
        UUID id,
        String ref) {}