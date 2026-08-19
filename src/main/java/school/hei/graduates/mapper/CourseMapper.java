package school.hei.graduates.mapper;

import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.CourseResponse;
import school.hei.graduates.endpoint.rest.model.UpsertCourse;
import school.hei.graduates.entity.Course;

@Component
public class CourseMapper {

  public Course toEntity(UpsertCourse request) {
    return Course.builder()
        .id(request.id())
        .ref(request.ref())
        .title(request.title())
        .credits(request.credits())
        .build();
  }

  public CourseResponse toResponse(Course course) {
    return new CourseResponse(
        course.getId(), course.getRef(), course.getTitle(), course.getCredits());
  }
}
