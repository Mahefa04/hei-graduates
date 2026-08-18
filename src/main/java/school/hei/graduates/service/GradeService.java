package school.hei.graduates.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.graduates.endpoint.rest.model.CreateGrade;
import school.hei.graduates.endpoint.rest.model.GradeHistoryResponse;
import school.hei.graduates.endpoint.rest.model.GradeResponse;
import school.hei.graduates.endpoint.rest.model.UpdateGrade;
import school.hei.graduates.entity.Exam;
import school.hei.graduates.entity.Grade;
import school.hei.graduates.entity.GradeHistory;
import school.hei.graduates.entity.Student;
import school.hei.graduates.exception.BadRequestException;
import school.hei.graduates.exception.ConflictException;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.GradeHistoryMapper;
import school.hei.graduates.mapper.GradeMapper;
import school.hei.graduates.repository.*;

@Service
@AllArgsConstructor
public class GradeService {

    private final TeacherRepository.GradeRepository gradeRepository;
    private final GradeHistoryRepository gradeHistoryRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final CourseOfferingRepository courseOfferingRepository;

    private final GradeMapper gradeMapper;
    private final GradeHistoryMapper gradeHistoryMapper;
    private final AuthorizationService authorizationService;

    public GradeResponse getById(
            UUID id,
            String email) {

        Grade grade =
                gradeRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Grade not found"));

        authorizationService.checkCanViewGrade(
                email,
                grade);

        return gradeMapper.toResponse(grade);
    }

    public List<GradeResponse> getByStudentId(
            UUID studentId,
            String email) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }

        authorizationService.checkCanViewStudentGrades(
                email,
                studentId);

        return gradeRepository
                .findByStudent_Id(studentId)
                .stream()
                .map(gradeMapper::toResponse)
                .toList();
    }

    public GradeResponse create(CreateGrade request, String email) {
        Student student =
                studentRepository
                        .findById(request.studentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Exam exam =
                examRepository
                        .findById(request.examId())
                        .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        authorizationService.checkCanManageGrade(email, exam);

        if (gradeRepository
                .findByStudent_IdAndExam_Id(request.studentId(), request.examId())
                .isPresent()) {

            throw new ConflictException(
                    "Student already has a grade for this exam");
        }

        Grade grade = gradeMapper.toEntity(request, student, exam);

        Grade savedGrade = gradeRepository.save(grade);

        return gradeMapper.toResponse(savedGrade);
    }

    @Transactional
    public GradeResponse update(
            UUID gradeId,
            UpdateGrade request,
            String modifiedBy) {

        if (request.reason() == null || request.reason().isBlank()) {
            throw new BadRequestException(
                    "A reason is required to update a grade");
        }

        Grade grade =
                gradeRepository
                        .findById(gradeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Grade not found"));

        authorizationService.checkCanManageGrade(modifiedBy, grade.getExam());

        if (grade.getValue().compareTo(request.value()) == 0) {
            throw new BadRequestException(
                    "The new grade is identical to the current grade");
        }

        GradeHistory history =
                GradeHistory.builder()
                        .grade(grade)
                        .oldValue(grade.getValue())
                        .newValue(request.value())
                        .reason(request.reason())
                        .modifiedBy(modifiedBy)
                        .modifiedAt(Instant.now())
                        .build();

        gradeHistoryRepository.save(history);

        grade.setValue(request.value());

        Grade savedGrade = gradeRepository.save(grade);

        return gradeMapper.toResponse(savedGrade);
    }

    public List<GradeHistoryResponse> getHistory(UUID gradeId, String email) {
        Grade grade =
                gradeRepository
                        .findById(gradeId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Grade not found"));

        authorizationService.checkCanViewGrade(email, grade);

        return gradeHistoryRepository
                .findByGrade_IdOrderByModifiedAtAsc(gradeId)
                .stream()
                .map(gradeHistoryMapper::toResponse)
                .toList();
    }

    public List<GradeResponse> getByCourseOfferingId(
            UUID courseOfferingId,
            String email) {

        if (!courseOfferingRepository.existsById(courseOfferingId)) {
            throw new ResourceNotFoundException(
                    "Course offering not found");
        }

        authorizationService.checkCanViewCourseOfferingGrades(
                email,
                courseOfferingId);

        return gradeRepository
                .findByExam_CourseOffering_Id(courseOfferingId)
                .stream()
                .map(gradeMapper::toResponse)
                .toList();
    }
}