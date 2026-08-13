package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.CourseResponse;
import school.hei.graduates.endpoint.rest.model.UpsertCourse;
import school.hei.graduates.entity.Course;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.CourseMapper;
import school.hei.graduates.repository.CourseRepository;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    public CourseResponse getById(UUID id) {
        Course course =
                courseRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        return courseMapper.toResponse(course);
    }

    public CourseResponse upsert(UpsertCourse request) {
        Course course = courseMapper.toEntity(request);

        Course savedCourse = courseRepository.save(course);

        return courseMapper.toResponse(savedCourse);
    }
}