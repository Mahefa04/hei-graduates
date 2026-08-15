package school.hei.graduates.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.CourseOfferingResponse;
import school.hei.graduates.endpoint.rest.model.UpsertCourseOffering;
import school.hei.graduates.entity.Course;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Group;

@Component
@AllArgsConstructor
public class CourseOfferingMapper {

    private final CourseMapper courseMapper;
    private final GroupMapper groupMapper;

    public CourseOffering toEntity(
            UpsertCourseOffering request,
            Course course,
            Group group) {

        return CourseOffering.builder()
                .id(request.id())
                .course(course)
                .group(group)
                .semester(request.semester())
                .academicYear(request.academicYear())
                .build();
    }

    public CourseOfferingResponse toResponse(CourseOffering courseOffering) {
        return new CourseOfferingResponse(
                courseOffering.getId(),
                courseMapper.toResponse(courseOffering.getCourse()),
                groupMapper.toResponse(courseOffering.getGroup()),
                courseOffering.getSemester(),
                courseOffering.getAcademicYear());
    }
}