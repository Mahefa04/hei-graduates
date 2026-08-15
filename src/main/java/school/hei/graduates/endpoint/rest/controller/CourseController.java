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
import school.hei.graduates.endpoint.rest.model.CourseResponse;
import school.hei.graduates.endpoint.rest.model.UpsertCourse;
import school.hei.graduates.entity.Course;
import school.hei.graduates.service.CourseService;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseResponse> getAll() {
        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable UUID id) {
        return courseService.getById(id);
    }

    @PutMapping
    public CourseResponse upsert(@RequestBody UpsertCourse request) {
        return courseService.upsert(request);
    }
}
