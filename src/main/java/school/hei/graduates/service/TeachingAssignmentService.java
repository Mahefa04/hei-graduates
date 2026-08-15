package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.TeachingAssignmentResponse;
import school.hei.graduates.endpoint.rest.model.UpsertTeachingAssignment;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Teacher;
import school.hei.graduates.entity.TeachingAssignment;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.TeachingAssignmentMapper;
import school.hei.graduates.repository.CourseOfferingRepository;
import school.hei.graduates.repository.TeacherRepository;
import school.hei.graduates.repository.TeachingAssignmentRepository;

@Service
@AllArgsConstructor
public class TeachingAssignmentService {

    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final CourseOfferingRepository courseOfferingRepository;
    private final TeacherRepository teacherRepository;
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
                                () ->
                                        new ResourceNotFoundException(
                                                "Teaching assignment not found"));

        return teachingAssignmentMapper.toResponse(assignment);
    }

    public List<TeachingAssignmentResponse> getByTeacherId(
            UUID teacherId) {

        if (!teacherRepository.existsById(teacherId)) {
            throw new ResourceNotFoundException("Teacher not found");
        }

        return teachingAssignmentRepository
                .findByTeacher_Id(teacherId)
                .stream()
                .map(teachingAssignmentMapper::toResponse)
                .toList();
    }

    public List<TeachingAssignmentResponse> getByCourseOfferingId(
            UUID courseOfferingId) {

        if (!courseOfferingRepository.existsById(courseOfferingId)) {
            throw new ResourceNotFoundException(
                    "Course offering not found");
        }

        return teachingAssignmentRepository
                .findByCourseOffering_Id(courseOfferingId)
                .stream()
                .map(teachingAssignmentMapper::toResponse)
                .toList();
    }

    public TeachingAssignmentResponse upsert(
            UpsertTeachingAssignment request) {

        CourseOffering courseOffering =
                courseOfferingRepository
                        .findById(request.courseOfferingId())
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Course offering not found"));

        Teacher teacher =
                teacherRepository
                        .findById(request.teacherId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Teacher not found"));

        TeachingAssignment assignment =
                teachingAssignmentMapper.toEntity(
                        request,
                        courseOffering,
                        teacher);

        return teachingAssignmentMapper.toResponse(
                teachingAssignmentRepository.save(assignment));
    }
}