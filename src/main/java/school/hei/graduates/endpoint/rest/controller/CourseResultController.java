package school.hei.graduates.endpoint.rest.controller;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.graduates.endpoint.rest.model.CourseResultResponse;
import school.hei.graduates.service.CourseResultService;

@RestController
@RequestMapping("/course-results")
@AllArgsConstructor
public class CourseResultController {

    private final CourseResultService courseResultService;

    @GetMapping("/student/{studentId}/course-offering/{courseOfferingId}")
    public CourseResultResponse getResult(
            @PathVariable UUID studentId,
            @PathVariable UUID courseOfferingId) {

        return courseResultService.getResult(
                studentId,
                courseOfferingId);
    }
}