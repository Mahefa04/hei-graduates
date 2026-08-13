package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.TeachingAssignmentResponse;
import school.hei.graduates.endpoint.rest.model.UpsertTeachingAssignment;
import school.hei.graduates.entity.Course;
import school.hei.graduates.entity.Group;
import school.hei.graduates.entity.Teacher;
import school.hei.graduates.entity.TeachingAssignment;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.TeachingAssignmentMapper;
import school.hei.graduates.repository.CourseRepository;
import school.hei.graduates.repository.GroupRepository;
import school.hei.graduates.repository.TeacherRepository;
import school.hei.graduates.repository.TeachingAssignmentRepository;

@Service
@AllArgsConstructor
public class TeachingAssignmentService {

    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final TeachingAssignmentMapper teachingAssignmentMapper;

    public List<TeachingAssignmentResponse> getAll() {
        return teachingAssignmentRepository.findAll().stream()
                .map(teachingAssignmentMapper::toResponse)
                .toList();
    }

    public TeachingAssignmentResponse getById(UUID id) {
        TeachingAssignment assignment =
                teachingAssignmentRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Teaching assignment not found"));

        return teachingAssignmentMapper.toResponse(assignment);
    }

    public List<TeachingAssignmentResponse> getByTeacherId(UUID teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new ResourceNotFoundException("Teacher not found");
        }

        return teachingAssignmentRepository.findByTeacher_Id(teacherId).stream()
                .map(teachingAssignmentMapper::toResponse)
                .toList();
    }

    public List<TeachingAssignmentResponse> getByCourseId(UUID courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found");
        }

        return teachingAssignmentRepository.findByCourse_Id(courseId).stream()
                .map(teachingAssignmentMapper::toResponse)
                .toList();
    }

    public TeachingAssignmentResponse upsert(UpsertTeachingAssignment request) {
        Course course =
                courseRepository
                        .findById(request.courseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Teacher teacher =
                teacherRepository
                        .findById(request.teacherId())
                        .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        Group group =
                groupRepository
                        .findById(request.groupId())
                        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        TeachingAssignment assignment =
                teachingAssignmentMapper.toEntity(request, course, teacher, group);

        TeachingAssignment savedAssignment =
                teachingAssignmentRepository.save(assignment);

        return teachingAssignmentMapper.toResponse(savedAssignment);
    }
}