package school.hei.graduates.endpoint.event.consumer.model;

import school.hei.graduates.PojaGenerated;
import school.hei.graduates.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
