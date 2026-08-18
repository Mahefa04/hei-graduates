package school.hei.graduates.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.StudentGroupHistoryResponse;
import school.hei.graduates.entity.Group;
import school.hei.graduates.entity.Student;
import school.hei.graduates.entity.StudentGroupHistory;
import school.hei.graduates.exception.BadRequestException;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.StudentGroupHistoryMapper;
import school.hei.graduates.repository.GroupRepository;
import school.hei.graduates.repository.StudentGroupHistoryRepository;
import school.hei.graduates.repository.StudentRepository;

@Service
@AllArgsConstructor
public class StudentGroupHistoryService {

  private final StudentGroupHistoryRepository historyRepository;
  private final StudentRepository studentRepository;
  private final GroupRepository groupRepository;
  private final StudentGroupHistoryMapper historyMapper;

  public List<StudentGroupHistoryResponse> getByStudentId(UUID studentId) {

    if (!studentRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Student not found");
    }

    return historyRepository.findByStudent_IdOrderByStartDateAsc(studentId).stream()
        .map(historyMapper::toResponse)
        .toList();
  }

  public StudentGroupHistoryResponse changeGroup(
      UUID studentId, UUID groupId, LocalDate changeDate) {

    Student student =
        studentRepository
            .findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

    Group newGroup =
        groupRepository
            .findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

    StudentGroupHistory current =
        historyRepository.findFirstByStudent_IdAndEndDateIsNull(studentId).orElse(null);

    if (current != null) {

      if (current.getGroup().getId().equals(groupId)) {
        throw new BadRequestException("Student is already in this group");
      }

      if (!changeDate.isAfter(current.getStartDate())) {
        throw new BadRequestException("Change date must be after current group start date");
      }

      current.setEndDate(changeDate.minusDays(1));

      historyRepository.save(current);
    }

    StudentGroupHistory newHistory =
        StudentGroupHistory.builder()
            .student(student)
            .group(newGroup)
            .startDate(changeDate)
            .endDate(null)
            .build();

    StudentGroupHistory saved = historyRepository.save(newHistory);

    return historyMapper.toResponse(saved);
  }

  public StudentGroupHistoryResponse getCurrentGroup(UUID studentId) {

    if (!studentRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Student not found");
    }

    StudentGroupHistory current =
        historyRepository
            .findFirstByStudent_IdAndEndDateIsNull(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student has no current group"));

    return historyMapper.toResponse(current);
  }
}
