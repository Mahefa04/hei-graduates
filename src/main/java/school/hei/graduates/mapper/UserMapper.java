package school.hei.graduates.mapper;

import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.CreateUser;
import school.hei.graduates.endpoint.rest.model.UserResponse;
import school.hei.graduates.entity.Student;
import school.hei.graduates.entity.Teacher;
import school.hei.graduates.entity.User;

@Component
public class UserMapper {

  public User toEntity(
      CreateUser request, String encodedPassword, Student student, Teacher teacher) {

    return User.builder()
        .email(request.email())
        .password(encodedPassword)
        .role(request.role())
        .student(student)
        .teacher(teacher)
        .build();
  }

  public UserResponse toResponse(User user) {
    return new UserResponse(
        user.getId(),
        user.getEmail(),
        user.getRole(),
        user.getStudent() == null ? null : user.getStudent().getId(),
        user.getTeacher() == null ? null : user.getTeacher().getId());
  }
}
