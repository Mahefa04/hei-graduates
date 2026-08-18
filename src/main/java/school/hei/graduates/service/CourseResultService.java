package school.hei.graduates.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.CourseResultResponse;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Exam;
import school.hei.graduates.entity.Grade;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.CourseOfferingMapper;
import school.hei.graduates.repository.*;

@Service
@AllArgsConstructor
public class CourseResultService {

    private final StudentRepository studentRepository;
    private final CourseOfferingRepository courseOfferingRepository;
    private final ExamRepository examRepository;
    private final TeacherRepository.GradeRepository gradeRepository;
    private final CourseOfferingMapper courseOfferingMapper;

    public CourseResultResponse getResult(
            UUID studentId,
            UUID courseOfferingId) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }

        CourseOffering courseOffering =
                courseOfferingRepository.findById(courseOfferingId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Course offering not found"));

        List<Exam> exams = examRepository.findByCourseOffering_Id(courseOfferingId);

        List<Grade> grades = gradeRepository.findByStudent_IdAndExam_CourseOffering_Id(studentId, courseOfferingId);

        BigDecimal weightedSum = BigDecimal.ZERO;
        BigDecimal completedCoefficient = BigDecimal.ZERO;

        for (Grade grade : grades) {
            BigDecimal coefficient =
                    grade.getExam().getCoefficient();

            BigDecimal weightedGrade =
                    grade.getValue().multiply(coefficient);

            weightedSum =
                    weightedSum.add(weightedGrade);

            completedCoefficient =
                    completedCoefficient.add(coefficient);
        }

        BigDecimal average = BigDecimal.ZERO;

        if (completedCoefficient.compareTo(BigDecimal.ZERO) > 0) {
            average =
                    weightedSum.divide(
                            completedCoefficient,
                            2,
                            RoundingMode.HALF_UP);
        }

        boolean complete =
                isComplete(exams, grades);

        return new CourseResultResponse(
                studentId,
                courseOfferingMapper.toResponse(courseOffering),
                average,
                complete);
    }

    private boolean isComplete(
            List<Exam> exams,
            List<Grade> grades) {

        BigDecimal totalCoefficient = BigDecimal.ZERO;

        for (Exam exam : exams) {
            totalCoefficient =
                    totalCoefficient.add(exam.getCoefficient());
        }

        boolean coefficientsComplete =
                totalCoefficient.compareTo(BigDecimal.ONE) == 0;

        boolean allGradesPresent =
                grades.size() == exams.size();

        return coefficientsComplete && allGradesPresent;
    }
}