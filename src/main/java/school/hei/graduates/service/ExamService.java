package school.hei.graduates.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.ExamResponse;
import school.hei.graduates.endpoint.rest.model.UpsertExam;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Exam;
import school.hei.graduates.exception.BadRequestException;
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
    return examRepository.findAll().stream().map(examMapper::toResponse).toList();
  }

  public ExamResponse getById(UUID id) {
    Exam exam =
        examRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

    return examMapper.toResponse(exam);
  }

  public List<ExamResponse> getByCourseOfferingId(UUID courseOfferingId) {
    if (!courseOfferingRepository.existsById(courseOfferingId)) {
      throw new ResourceNotFoundException("Course offering not found");
    }

    return examRepository.findByCourseOffering_Id(courseOfferingId).stream()
        .map(examMapper::toResponse)
        .toList();
  }

  public ExamResponse upsert(UpsertExam request) {
    CourseOffering courseOffering =
        courseOfferingRepository
            .findById(request.courseOfferingId())
            .orElseThrow(() -> new ResourceNotFoundException("Course offering not found"));

    validateCoefficient(request);

    Exam exam = examMapper.toEntity(request, courseOffering);

    exam = examRepository.save(exam);

    return examMapper.toResponse(exam);
  }

  private void validateCoefficient(UpsertExam request) {
    List<Exam> exams = examRepository.findByCourseOffering_Id(request.courseOfferingId());

    BigDecimal total = BigDecimal.ZERO;

    for (Exam exam : exams) {
      if (request.id() == null || !exam.getId().equals(request.id())) {

        total = total.add(exam.getCoefficient());
      }
    }

    total = total.add(request.coefficient());

    if (total.compareTo(BigDecimal.ONE) > 0) {
      throw new BadRequestException("The sum of exam coefficients cannot exceed 1");
    }
  }

  public boolean isCourseOfferingComplete(UUID courseOfferingId) {
    if (!courseOfferingRepository.existsById(courseOfferingId)) {
      throw new ResourceNotFoundException("Course offering not found");
    }

    List<Exam> exams = examRepository.findByCourseOffering_Id(courseOfferingId);

    BigDecimal total = BigDecimal.ZERO;

    for (Exam exam : exams) {
      total = total.add(exam.getCoefficient());
    }

    return total.compareTo(BigDecimal.ONE) == 0;
  }
}
