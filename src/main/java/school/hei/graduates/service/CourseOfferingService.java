package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.CourseOfferingResponse;
import school.hei.graduates.endpoint.rest.model.UpsertCourseOffering;
import school.hei.graduates.entity.Course;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Group;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.CourseOfferingMapper;
import school.hei.graduates.repository.CourseOfferingRepository;
import school.hei.graduates.repository.CourseRepository;
import school.hei.graduates.repository.GroupRepository;

@Service
@AllArgsConstructor
public class CourseOfferingService {

  private final CourseOfferingRepository courseOfferingRepository;
  private final CourseRepository courseRepository;
  private final GroupRepository groupRepository;
  private final CourseOfferingMapper courseOfferingMapper;

  public List<CourseOfferingResponse> getAll() {
    return courseOfferingRepository.findAll().stream()
        .map(courseOfferingMapper::toResponse)
        .toList();
  }

  public CourseOfferingResponse getById(UUID id) {
    CourseOffering courseOffering =
        courseOfferingRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course offering not found"));

    return courseOfferingMapper.toResponse(courseOffering);
  }

  public CourseOfferingResponse upsert(UpsertCourseOffering request) {

    Course course =
        courseRepository
            .findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

    Group group =
        groupRepository
            .findById(request.groupId())
            .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

    CourseOffering courseOffering = courseOfferingMapper.toEntity(request, course, group);

    CourseOffering savedCourseOffering = courseOfferingRepository.save(courseOffering);

    return courseOfferingMapper.toResponse(savedCourseOffering);
  }
}
