package school.hei.graduates.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.graduates.endpoint.rest.model.GroupResponse;
import school.hei.graduates.endpoint.rest.model.UpsertGroup;
import school.hei.graduates.service.GroupService;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public List<GroupResponse> getAll() {
        return groupService.getAll();
    }

    @GetMapping("/{id}")
    public GroupResponse getById(@PathVariable UUID id) {
        return groupService.getById(id);
    }

    @PutMapping
    public GroupResponse upsert(
            @Valid @RequestBody UpsertGroup request) {
        return groupService.upsert(request);
    }
}