package school.hei.graduates.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.graduates.endpoint.rest.model.StudentGroupHistoryResponse;
import school.hei.graduates.endpoint.rest.model.StudentResponse;
import school.hei.graduates.endpoint.rest.model.UpsertStudent;
import school.hei.graduates.entity.Group;
import school.hei.graduates.entity.Promotion;
import school.hei.graduates.entity.Student;
import school.hei.graduates.entity.StudentGroupHistory;
import school.hei.graduates.exception.ConflictException;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.StudentGroupHistoryMapper;
import school.hei.graduates.mapper.StudentMapper;
import school.hei.graduates.repository.GroupRepository;
import school.hei.graduates.repository.PromotionRepository;
import school.hei.graduates.repository.StudentGroupHistoryRepository;
import school.hei.graduates.repository.StudentRepository;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PromotionRepository promotionRepository;
    private final GroupRepository groupRepository;
    private final StudentGroupHistoryRepository studentGroupHistoryRepository;

    private final StudentMapper studentMapper;
    private final StudentGroupHistoryMapper studentGroupHistoryMapper;

    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    public StudentResponse getById(UUID id) {
        Student student =
                studentRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return studentMapper.toResponse(student);
    }

    public List<StudentResponse> getByPromotionId(UUID promotionId) {
        if (!promotionRepository.existsById(promotionId)) {
            throw new ResourceNotFoundException("Promotion not found");
        }

        return studentRepository.findByPromotion_Id(promotionId).stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    public StudentResponse upsert(UpsertStudent request) {
        Promotion promotion =
                promotionRepository
                        .findById(request.promotionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Promotion not found"));

        Student student = studentMapper.toEntity(request, promotion);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.toResponse(savedStudent);
    }

    public List<StudentGroupHistoryResponse> getGroupHistory(UUID studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }

        return studentGroupHistoryRepository
                .findByStudent_IdOrderByStartDateAsc(studentId)
                .stream()
                .map(studentGroupHistoryMapper::toResponse)
                .toList();
    }

    @Transactional
    public StudentGroupHistoryResponse changeGroup(UUID studentId, UUID groupId) {
        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Group newGroup =
                groupRepository
                        .findById(groupId)
                        .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        LocalDate now = LocalDate.now();

        var currentGroup =
                studentGroupHistoryRepository
                        .findFirstByStudent_IdAndEndDateIsNull(studentId);

        if (currentGroup.isPresent()) {
            StudentGroupHistory currentHistory = currentGroup.get();

            if (currentHistory.getGroup().getId().equals(groupId)) {
                throw new ConflictException("Student is already in this group");
            }

            currentHistory.setEndDate(now);

            studentGroupHistoryRepository.save(currentHistory);
        }

        StudentGroupHistory newHistory =
                StudentGroupHistory.builder()
                        .student(student)
                        .group(newGroup)
                        .startDate(now)
                        .endDate(null)
                        .build();

        StudentGroupHistory savedHistory =
                studentGroupHistoryRepository.save(newHistory);

        return studentGroupHistoryMapper.toResponse(savedHistory);
    }
}