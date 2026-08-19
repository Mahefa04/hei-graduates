package school.hei.graduates.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.hei.graduates.endpoint.rest.model.TeacherResponse;
import school.hei.graduates.endpoint.rest.model.UpsertTeacher;
import school.hei.graduates.service.TeacherService;

@RestController
@RequestMapping("/teachers")
@AllArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @GetMapping
  public List<TeacherResponse> getAll() {
    return teacherService.getAll();
  }

  @GetMapping("/{id}")
  public TeacherResponse getById(@PathVariable UUID id) {
    return teacherService.getById(id);
  }

  @PutMapping
  public TeacherResponse upsert(@Valid @RequestBody UpsertTeacher request) {
    return teacherService.upsert(request);
  }
}
