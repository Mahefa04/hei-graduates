package school.hei.graduates.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.graduates.endpoint.rest.model.CreateGrade;
import school.hei.graduates.endpoint.rest.model.GradeHistoryResponse;
import school.hei.graduates.endpoint.rest.model.GradeResponse;
import school.hei.graduates.endpoint.rest.model.UpdateGrade;
import school.hei.graduates.service.GradeService;

@RestController
@RequestMapping("/grades")
@AllArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @GetMapping("/{id}")
    public GradeResponse getById(@PathVariable UUID id, Principal principal) {
        return gradeService.getById(id, principal.getName());
    }

    @GetMapping("/student/{studentId}")
    public List<GradeResponse> getByStudentId(
            @PathVariable UUID studentId, Principal principal) {
        return gradeService.getByStudentId(studentId, principal.getName());
    }

    @PostMapping
    public GradeResponse create(
            @Valid @RequestBody CreateGrade request, Principal principal) {
        return gradeService.create(request, principal.getName());
    }

    @PatchMapping("/{id}")
    public GradeResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateGrade request, Principal principal) {
        return gradeService.update(id, request, principal.getName());
    }

    @GetMapping("/{id}/history")
    public List<GradeHistoryResponse> getHistory(
            @PathVariable UUID id, Principal principal) {
        return gradeService.getHistory(id, principal.getName());
    }
}