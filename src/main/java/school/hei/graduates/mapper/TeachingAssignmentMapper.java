package school.hei.graduates.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.TeachingAssignmentResponse;
import school.hei.graduates.endpoint.rest.model.UpsertTeachingAssignment;
import school.hei.graduates.entity.Course;
import school.hei.graduates.entity.Group;
import school.hei.graduates.entity.Teacher;
import school.hei.graduates.entity.TeachingAssignment;

@Component
@AllArgsConstructor
public class TeachingAssignmentMapper {

    private final CourseMapper courseMapper;
    private final TeacherMapper teacherMapper;
    private final GroupMapper groupMapper;

    public TeachingAssignment toEntity(
            UpsertTeachingAssignment request,
            Course course,
            Teacher teacher,
            Group group) {

        return TeachingAssignment.builder()
                .id(request.id())
                .course(course)
                .teacher(teacher)
                .group(group)
                .build();
    }

    public TeachingAssignmentResponse toResponse(
            TeachingAssignment assignment) {

        return new TeachingAssignmentResponse(
                assignment.getId(),
                courseMapper.toResponse(assignment.getCourse()),
                teacherMapper.toResponse(assignment.getTeacher()),
                groupMapper.toResponse(assignment.getGroup()));
    }
}