package school.hei.graduates.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.TeachingAssignmentResponse;
import school.hei.graduates.endpoint.rest.model.UpsertTeachingAssignment;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Teacher;
import school.hei.graduates.entity.TeachingAssignment;

@Component
@AllArgsConstructor
public class TeachingAssignmentMapper {

    private final CourseOfferingMapper courseOfferingMapper;
    private final TeacherMapper teacherMapper;

    public TeachingAssignment toEntity(
            UpsertTeachingAssignment request,
            CourseOffering courseOffering,
            Teacher teacher) {

        return TeachingAssignment.builder()
                .id(request.id())
                .courseOffering(courseOffering)
                .teacher(teacher)
                .build();
    }

    public TeachingAssignmentResponse toResponse(
            TeachingAssignment assignment) {

        return new TeachingAssignmentResponse(
                assignment.getId(),
                courseOfferingMapper.toResponse(
                        assignment.getCourseOffering()),
                teacherMapper.toResponse(
                        assignment.getTeacher()));
    }
}