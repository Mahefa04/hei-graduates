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
import school.hei.graduates.endpoint.rest.model.ExamResponse;
import school.hei.graduates.endpoint.rest.model.UpsertExam;
import school.hei.graduates.service.ExamService;

@RestController
@RequestMapping("/exams")
@AllArgsConstructor
public class ExamController {

    private final ExamService examService;

    @GetMapping
    public List<ExamResponse> getAll() {
        return examService.getAll();
    }

    @GetMapping("/{id}")
    public ExamResponse getById(@PathVariable UUID id) {
        return examService.getById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<ExamResponse> getByCourseId(
            @PathVariable UUID courseId) {
        return examService.getByCourseId(courseId);
    }

    @PutMapping
    public ExamResponse upsert(
            @Valid @RequestBody UpsertExam request) {
        return examService.upsert(request);
    }
}