package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.GroupResponse;
import school.hei.graduates.endpoint.rest.model.UpsertGroup;
import school.hei.graduates.entity.Group;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.GroupMapper;
import school.hei.graduates.repository.GroupRepository;

@Service
@AllArgsConstructor
public class GroupService {

  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;

  public List<GroupResponse> getAll() {
    return groupRepository.findAll().stream().map(groupMapper::toResponse).toList();
  }

  public GroupResponse getById(UUID id) {
    Group group =
        groupRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

    return groupMapper.toResponse(group);
  }

  public GroupResponse upsert(UpsertGroup request) {
    Group group = groupMapper.toEntity(request);

    Group savedGroup = groupRepository.save(group);

    return groupMapper.toResponse(savedGroup);
  }
}
