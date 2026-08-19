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
import school.hei.graduates.endpoint.rest.model.ChangeStudentGroup;
import school.hei.graduates.endpoint.rest.model.StudentGroupHistoryResponse;
import school.hei.graduates.endpoint.rest.model.StudentResponse;
import school.hei.graduates.endpoint.rest.model.UpsertStudent;
import school.hei.graduates.service.StudentService;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping
  public List<StudentResponse> getAll() {
    return studentService.getAll();
  }

  @GetMapping("/{id}")
  public StudentResponse getById(@PathVariable UUID id) {
    return studentService.getById(id);
  }

  @GetMapping("/promotion/{promotionId}")
  public List<StudentResponse> getByPromotionId(@PathVariable UUID promotionId) {
    return studentService.getByPromotionId(promotionId);
  }

  @PutMapping
  public StudentResponse upsert(@Valid @RequestBody UpsertStudent request) {
    return studentService.upsert(request);
  }

  @GetMapping("/{studentId}/group-history")
  public List<StudentGroupHistoryResponse> getGroupHistory(@PathVariable UUID studentId) {
    return studentService.getGroupHistory(studentId);
  }

  @PutMapping("/{studentId}/group")
  public StudentGroupHistoryResponse changeGroup(
      @PathVariable UUID studentId, @Valid @RequestBody ChangeStudentGroup request) {

    return studentService.changeGroup(studentId, request.groupId());
  }
}
