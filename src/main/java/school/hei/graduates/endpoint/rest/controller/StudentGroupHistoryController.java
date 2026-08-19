package school.hei.graduates.endpoint.rest.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import school.hei.graduates.endpoint.rest.model.StudentGroupHistoryResponse;
import school.hei.graduates.service.StudentGroupHistoryService;

@RestController
@RequestMapping("/student-group-history")
@AllArgsConstructor
public class StudentGroupHistoryController {

  private final StudentGroupHistoryService historyService;

  @GetMapping("/student/{studentId}")
  public List<StudentGroupHistoryResponse> getByStudentId(@PathVariable UUID studentId) {

    return historyService.getByStudentId(studentId);
  }

  @GetMapping("/student/{studentId}/current")
  public StudentGroupHistoryResponse getCurrentGroup(@PathVariable UUID studentId) {

    return historyService.getCurrentGroup(studentId);
  }

  @PutMapping("/student/{studentId}/group/{groupId}")
  public StudentGroupHistoryResponse changeGroup(
      @PathVariable UUID studentId,
      @PathVariable UUID groupId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate changeDate) {

    return historyService.changeGroup(studentId, groupId, changeDate);
  }
}
