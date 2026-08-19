package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.CreateUser;
import school.hei.graduates.endpoint.rest.model.UserResponse;
import school.hei.graduates.entity.Role;
import school.hei.graduates.entity.Student;
import school.hei.graduates.entity.Teacher;
import school.hei.graduates.entity.User;
import school.hei.graduates.exception.BadRequestException;
import school.hei.graduates.exception.ConflictException;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.UserMapper;
import school.hei.graduates.repository.StudentRepository;
import school.hei.graduates.repository.TeacherRepository;
import school.hei.graduates.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public List<UserResponse> getAll() {
    return userRepository.findAll().stream().map(userMapper::toResponse).toList();
  }

  public UserResponse getById(UUID id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    return userMapper.toResponse(user);
  }

  public UserResponse create(CreateUser request) {

    if (userRepository.existsByEmail(request.email())) {
      throw new ConflictException("Email already used");
    }

    Student student = null;
    Teacher teacher = null;

    if (request.role() == Role.STUDENT) {

      if (request.studentId() == null) {
        throw new BadRequestException("studentId is required for STUDENT");
      }

      student =
          studentRepository
              .findById(request.studentId())
              .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    if (request.role() == Role.TEACHER) {

      if (request.teacherId() == null) {
        throw new BadRequestException("teacherId is required for TEACHER");
      }

      teacher =
          teacherRepository
              .findById(request.teacherId())
              .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    String encodedPassword = passwordEncoder.encode(request.password());

    User user = userMapper.toEntity(request, encodedPassword, student, teacher);

    User savedUser = userRepository.save(user);

    return userMapper.toResponse(savedUser);
  }
}
