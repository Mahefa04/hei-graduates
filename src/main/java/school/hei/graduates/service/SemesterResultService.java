package school.hei.graduates.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.CourseResultResponse;
import school.hei.graduates.endpoint.rest.model.SemesterResultResponse;
import school.hei.graduates.entity.CourseOffering;
import school.hei.graduates.entity.Semester;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.repository.CourseOfferingRepository;
import school.hei.graduates.repository.StudentRepository;

@Service
@AllArgsConstructor
public class SemesterResultService {

    private final StudentRepository studentRepository;
    private final CourseOfferingRepository courseOfferingRepository;
    private final CourseResultService courseResultService;

    public SemesterResultResponse getResult(
            UUID studentId,
            String academicYear,
            Semester semester) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException(
                    "Student not found");
        }

        List<CourseOffering> courseOfferings =
                courseOfferingRepository
                        .findByAcademicYearAndSemester(
                                academicYear,
                                semester);

        List<CourseResultResponse> results =
                new ArrayList<>();

        for (CourseOffering courseOffering : courseOfferings) {

            try {
                CourseResultResponse result =
                        courseResultService.getResult(
                                studentId,
                                courseOffering.getId());

                results.add(result);

            } catch (Exception ignored) {}
        }

        BigDecimal weightedTotal = BigDecimal.ZERO;
        int totalCredits = 0;
        boolean complete = true;

        for (CourseResultResponse result : results) {

            int credits =
                    result.courseOffering()
                            .course()
                            .credits();

            weightedTotal =
                    weightedTotal.add(
                            result.average()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    credits)));

            totalCredits += credits;

            if (!result.complete()) {
                complete = false;
            }
        }

        BigDecimal average = BigDecimal.ZERO;

        if (totalCredits > 0) {
            average =
                    weightedTotal.divide(
                            BigDecimal.valueOf(totalCredits),
                            2,
                            RoundingMode.HALF_UP);
        }

        return new SemesterResultResponse(
                academicYear,
                semester.name(),
                average,
                totalCredits,
                complete,
                results);
    }
}