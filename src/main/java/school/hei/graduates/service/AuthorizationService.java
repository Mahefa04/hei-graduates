package school.hei.graduates.service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.entity.Exam;
import school.hei.graduates.entity.Grade;
import school.hei.graduates.entity.Role;
import school.hei.graduates.entity.User;
import school.hei.graduates.exception.ForbiddenException;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.repository.TeachingAssignmentRepository;
import school.hei.graduates.repository.UserRepository;

@Service
@AllArgsConstructor
public class AuthorizationService {

  private final UserRepository userRepository;
  private final TeachingAssignmentRepository teachingAssignmentRepository;

  private User getUser(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  public void checkCanViewStudentGrades(String email, UUID studentId) {

    User user = getUser(email);

    if (user.getRole() == Role.ADMIN) {
      return;
    }

    if (user.getRole() == Role.STUDENT
        && user.getStudent() != null
        && user.getStudent().getId().equals(studentId)) {
      return;
    }

    throw new ForbiddenException("You are not allowed to view these grades");
  }

  public void checkCanViewGrade(String email, Grade grade) {

    User user = getUser(email);

    if (user.getRole() == Role.ADMIN) {
      return;
    }

    if (user.getRole() == Role.STUDENT
        && user.getStudent() != null
        && user.getStudent().getId().equals(grade.getStudent().getId())) {
      return;
    }

    if (user.getRole() == Role.TEACHER && user.getTeacher() != null) {

      UUID courseOfferingId = grade.getExam().getCourseOffering().getId();

      boolean teachesCourse =
          teachingAssignmentRepository.existsByTeacher_IdAndCourseOffering_Id(
              user.getTeacher().getId(), courseOfferingId);

      if (teachesCourse) {
        return;
      }
    }

    throw new ForbiddenException("You are not allowed to view this grade");
  }

  public void checkCanManageGrade(String email, Exam exam) {

    User user = getUser(email);

    if (user.getRole() == Role.ADMIN) {
      return;
    }

    if (user.getRole() == Role.TEACHER && user.getTeacher() != null) {

      UUID courseOfferingId = exam.getCourseOffering().getId();

      boolean teachesCourse =
          teachingAssignmentRepository.existsByTeacher_IdAndCourseOffering_Id(
              user.getTeacher().getId(), courseOfferingId);

      if (teachesCourse) {
        return;
      }
    }

    throw new ForbiddenException("You are not allowed to manage this grade");
  }

  public void checkCanViewCourseOfferingGrades(String email, UUID courseOfferingId) {

    User user = getUser(email);

    if (user.getRole() == Role.ADMIN) {
      return;
    }

    if (user.getRole() == Role.TEACHER && user.getTeacher() != null) {

      boolean teachesCourse =
          teachingAssignmentRepository.existsByTeacher_IdAndCourseOffering_Id(
              user.getTeacher().getId(), courseOfferingId);

      if (teachesCourse) {
        return;
      }
    }

    throw new ForbiddenException("You are not allowed to view grades for this course offering");
  }
}
