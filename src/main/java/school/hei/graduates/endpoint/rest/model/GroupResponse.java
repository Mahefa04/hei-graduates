package school.hei.graduates.endpoint.rest.model;

import java.util.UUID;
import school.hei.graduates.entity.Track;

public record GroupResponse(UUID id, String ref, Track track) {}
