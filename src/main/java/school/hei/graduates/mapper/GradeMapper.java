package school.hei.graduates.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.CreateGrade;
import school.hei.graduates.endpoint.rest.model.GradeResponse;
import school.hei.graduates.entity.Exam;
import school.hei.graduates.entity.Grade;
import school.hei.graduates.entity.Student;

@Component
@AllArgsConstructor
public class GradeMapper {

  private final StudentMapper studentMapper;
  private final ExamMapper examMapper;

  public Grade toEntity(CreateGrade request, Student student, Exam exam) {

    return Grade.builder().student(student).exam(exam).value(request.value()).build();
  }

  public GradeResponse toResponse(Grade grade) {
    return new GradeResponse(
        grade.getId(),
        studentMapper.toResponse(grade.getStudent()),
        examMapper.toResponse(grade.getExam()),
        grade.getValue());
  }
}
