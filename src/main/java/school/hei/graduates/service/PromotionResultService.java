package school.hei.graduates.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.SemesterResultResponse;
import school.hei.graduates.entity.*;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.repository.*;

@Service
@AllArgsConstructor
public class PromotionResultService {

  private final PromotionRepository promotionRepository;
  private final StudentRepository studentRepository;
  private final SemesterResultService semesterResultService;
  private final CourseOfferingRepository courseOfferingRepository;
  private final StudentGroupHistoryRepository historyRepository;
  private final GradeRepository gradeRepository;

  public List<SemesterResultResponse> getStudentResults(UUID promotionId, UUID studentId) {

    Promotion promotion =
        promotionRepository
            .findById(promotionId)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion not found"));

    if (!studentRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Student not found");
    }

    List<SemesterResultResponse> results = new ArrayList<>();

    for (Semester semester : Semester.values()) {

      String academicYear = getAcademicYear(promotion, semester);

      SemesterResultResponse result =
          semesterResultService.getResult(studentId, academicYear, semester);

      results.add(result);
    }

    return results;
  }

  public BigDecimal calculatePromotionAverage(UUID promotionId, UUID studentId) {

    List<SemesterResultResponse> results = getStudentResults(promotionId, studentId);

    BigDecimal weightedTotal = BigDecimal.ZERO;
    int totalCredits = 0;

    for (SemesterResultResponse result : results) {

      weightedTotal =
          weightedTotal.add(result.average().multiply(BigDecimal.valueOf(result.credits())));

      totalCredits += result.credits();
    }

    if (totalCredits == 0) {
      return BigDecimal.ZERO;
    }

    return weightedTotal.divide(BigDecimal.valueOf(totalCredits), 2, RoundingMode.HALF_UP);
  }

  private String getAcademicYear(Promotion promotion, Semester semester) {

    int yearOffset = (semester.ordinal()) / 2;

    int startYear = promotion.getStartYear() + yearOffset;
    int endYear = startYear + 1;

    return startYear + "-" + endYear;
  }

  public List<Student> getGraduates(UUID promotionId) {

    Promotion promotion =
        promotionRepository
            .findById(promotionId)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion not found"));

    List<Student> students = studentRepository.findByPromotion_Id(promotion.getId());

    List<Student> graduates = new ArrayList<>();

    for (Student student : students) {

      if (hasPassedAllCourses(student, promotion)) {
        graduates.add(student);
      }
    }

    return graduates;
  }

  private boolean hasPassedAllCourses(Student student, Promotion promotion) {

    List<StudentGroupHistory> histories =
        historyRepository.findByStudent_IdOrderByStartDateAsc(student.getId());

    if (histories.isEmpty()) {
      return false;
    }

    for (StudentGroupHistory history : histories) {

      List<CourseOffering> courseOfferings =
          courseOfferingRepository.findByGroup_Id(history.getGroup().getId());

      for (CourseOffering courseOffering : courseOfferings) {

        if (!belongsToPromotion(courseOffering, promotion)) {
          continue;
        }

        List<Grade> grades =
            gradeRepository.findByStudent_IdAndExam_CourseOffering_Id(
                student.getId(), courseOffering.getId());

        if (grades.isEmpty()) {
          return false;
        }

        for (Grade grade : grades) {

          if (grade.getValue().compareTo(BigDecimal.TEN) < 0) {
            return false;
          }
        }
      }
    }

    return true;
  }

  private boolean belongsToPromotion(CourseOffering courseOffering, Promotion promotion) {

    String academicYear = courseOffering.getAcademicYear();

    if (academicYear == null || academicYear.isBlank()) {
      return false;
    }

    String[] years = academicYear.split("-");

    if (years.length != 2) {
      return false;
    }

    int startYear = Integer.parseInt(years[0]);

    return startYear >= promotion.getStartYear() && startYear < promotion.getStartYear() + 3;
  }
}
