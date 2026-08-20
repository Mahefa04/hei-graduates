package school.hei.graduates.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.SemesterResultResponse;
import school.hei.graduates.entity.Promotion;
import school.hei.graduates.entity.Semester;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.repository.PromotionRepository;
import school.hei.graduates.repository.StudentRepository;

@Service
@AllArgsConstructor
public class PromotionResultService {

    private final PromotionRepository promotionRepository;
    private final StudentRepository studentRepository;
    private final SemesterResultService semesterResultService;

    public List<SemesterResultResponse> getStudentResults(
            UUID promotionId, UUID studentId) {

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
                    semesterResultService.getResult(
                            studentId,
                            academicYear,
                            semester);

            results.add(result);
        }

        return results;
    }

    public BigDecimal calculatePromotionAverage(
            UUID promotionId, UUID studentId) {

        List<SemesterResultResponse> results =
                getStudentResults(promotionId, studentId);

        BigDecimal weightedTotal = BigDecimal.ZERO;
        int totalCredits = 0;

        for (SemesterResultResponse result : results) {

            weightedTotal =
                    weightedTotal.add(
                            result.average()
                                    .multiply(BigDecimal.valueOf(result.credits())));

            totalCredits += result.credits();
        }

        if (totalCredits == 0) {
            return BigDecimal.ZERO;
        }

        return weightedTotal.divide(
                BigDecimal.valueOf(totalCredits),
                2,
                RoundingMode.HALF_UP);
    }

    private String getAcademicYear(Promotion promotion, Semester semester) {

        int yearOffset = (semester.ordinal()) / 2;

        int startYear = promotion.getStartYear() + yearOffset;
        int endYear = startYear + 1;

        return startYear + "-" + endYear;
    }
}