package school.hei.graduates.endpoint.rest.controller;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.graduates.endpoint.rest.model.SemesterResultResponse;
import school.hei.graduates.entity.Semester;
import school.hei.graduates.service.SemesterResultService;

@RestController
@RequestMapping("/semester-results")
@AllArgsConstructor
public class SemesterResultController {

  private final SemesterResultService semesterResultService;

  @GetMapping("/student/{studentId}")
  public SemesterResultResponse getResult(
      @PathVariable UUID studentId,
      @RequestParam String academicYear,
      @RequestParam Semester semester) {

    return semesterResultService.getResult(studentId, academicYear, semester);
  }
}
