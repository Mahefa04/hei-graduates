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
import school.hei.graduates.endpoint.rest.model.TeachingAssignmentResponse;
import school.hei.graduates.endpoint.rest.model.UpsertTeachingAssignment;
import school.hei.graduates.service.TeachingAssignmentService;

@RestController
@RequestMapping("/teaching-assignments")
@AllArgsConstructor
public class TeachingAssignmentController {

  private final TeachingAssignmentService teachingAssignmentService;

  @GetMapping
  public List<TeachingAssignmentResponse> getAll() {
    return teachingAssignmentService.getAll();
  }

  @GetMapping("/{id}")
  public TeachingAssignmentResponse getById(@PathVariable UUID id) {
    return teachingAssignmentService.getById(id);
  }

  @GetMapping("/teacher/{teacherId}")
  public List<TeachingAssignmentResponse> getByTeacherId(@PathVariable UUID teacherId) {
    return teachingAssignmentService.getByTeacherId(teacherId);
  }

  @GetMapping("/course-offering/{courseOfferingId}")
  public List<TeachingAssignmentResponse> getByCourseOfferingId(
      @PathVariable UUID courseOfferingId) {
    return teachingAssignmentService.getByCourseOfferingId(courseOfferingId);
  }

  @PutMapping
  public TeachingAssignmentResponse upsert(@Valid @RequestBody UpsertTeachingAssignment request) {
    return teachingAssignmentService.upsert(request);
  }
}
