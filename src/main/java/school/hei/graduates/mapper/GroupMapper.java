package school.hei.graduates.mapper;

import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.GroupResponse;
import school.hei.graduates.endpoint.rest.model.UpsertGroup;
import school.hei.graduates.entity.Group;

@Component
public class GroupMapper {

  public Group toEntity(UpsertGroup request) {
    return Group.builder().id(request.id()).ref(request.ref()).track(request.track()).build();
  }

  public GroupResponse toResponse(Group group) {
    return new GroupResponse(group.getId(), group.getRef(), group.getTrack());
  }
}
