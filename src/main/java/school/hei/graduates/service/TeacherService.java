package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.TeacherResponse;
import school.hei.graduates.endpoint.rest.model.UpsertTeacher;
import school.hei.graduates.entity.Teacher;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.TeacherMapper;
import school.hei.graduates.repository.TeacherRepository;

@Service
@AllArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public List<TeacherResponse> getAll() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toResponse)
                .toList();
    }

    public TeacherResponse getById(UUID id) {
        Teacher teacher =
                teacherRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        return teacherMapper.toResponse(teacher);
    }

    public TeacherResponse upsert(UpsertTeacher request) {
        Teacher teacher = teacherMapper.toEntity(request);

        Teacher savedTeacher = teacherRepository.save(teacher);

        return teacherMapper.toResponse(savedTeacher);
    }
}