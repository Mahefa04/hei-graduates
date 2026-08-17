package school.hei.graduates.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.hei.graduates.endpoint.rest.model.CreateUser;
import school.hei.graduates.endpoint.rest.model.UserResponse;
import school.hei.graduates.service.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @PostMapping
    public UserResponse create(
            @Valid @RequestBody CreateUser request) {
        return userService.create(request);
    }
}