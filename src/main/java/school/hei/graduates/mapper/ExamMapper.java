package school.hei.graduates.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.ExamResponse;
import school.hei.graduates.endpoint.rest.model.UpsertExam;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Exam;

@Component
@AllArgsConstructor
public class ExamMapper {

  private final CourseOfferingMapper courseOfferingMapper;

  public Exam toEntity(UpsertExam request, CourseOffering courseOffering) {

    return Exam.builder()
        .id(request.id())
        .title(request.title())
        .examDate(request.examDate())
        .coefficient(request.coefficient())
        .courseOffering(courseOffering)
        .build();
  }

  public ExamResponse toResponse(Exam exam) {
    return new ExamResponse(
        exam.getId(),
        exam.getTitle(),
        exam.getExamDate(),
        exam.getCoefficient(),
        courseOfferingMapper.toResponse(exam.getCourseOffering()));
  }
}
