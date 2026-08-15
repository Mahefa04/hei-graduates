package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.ExamResponse;
import school.hei.graduates.endpoint.rest.model.UpsertExam;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Exam;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.ExamMapper;
import school.hei.graduates.repository.CourseOfferingRepository;
import school.hei.graduates.repository.ExamRepository;

@Service
@AllArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final CourseOfferingRepository courseOfferingRepository;
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
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Exam not found"));

        return examMapper.toResponse(exam);
    }

    public List<ExamResponse> getByCourseOfferingId(
            UUID courseOfferingId) {

        if (!courseOfferingRepository.existsById(courseOfferingId)) {
            throw new ResourceNotFoundException(
                    "Course offering not found");
        }

        return examRepository
                .findByCourseOffering_Id(courseOfferingId)
                .stream()
                .map(examMapper::toResponse)
                .toList();
    }

    public ExamResponse upsert(UpsertExam request) {

        CourseOffering courseOffering =
                courseOfferingRepository
                        .findById(request.courseOfferingId())
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Course offering not found"));

        Exam exam =
                examMapper.toEntity(
                        request,
                        courseOffering);

        Exam savedExam = examRepository.save(exam);

        return examMapper.toResponse(savedExam);
    }
}