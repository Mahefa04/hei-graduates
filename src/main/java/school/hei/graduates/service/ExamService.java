package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.ExamResponse;
import school.hei.graduates.endpoint.rest.model.UpsertExam;
import school.hei.graduates.entity.Course;
import school.hei.graduates.entity.Exam;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.ExamMapper;
import school.hei.graduates.repository.CourseRepository;
import school.hei.graduates.repository.ExamRepository;

@Service
@AllArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final ExamMapper examMapper;

    public List<ExamResponse> getAll() {
        return examRepository.findAll().stream()
                .map(examMapper::toResponse)
                .toList();
    }

    public ExamResponse getById(UUID id) {
        Exam exam =
                examRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        return examMapper.toResponse(exam);
    }

    public List<ExamResponse> getByCourseId(UUID courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found");
        }

        return examRepository.findByCourse_Id(courseId).stream()
                .map(examMapper::toResponse)
                .toList();
    }

    public ExamResponse upsert(UpsertExam request) {
        Course course =
                courseRepository
                        .findById(request.courseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Exam exam = examMapper.toEntity(request, course);

        Exam savedExam = examRepository.save(exam);

        return examMapper.toResponse(savedExam);
    }
}