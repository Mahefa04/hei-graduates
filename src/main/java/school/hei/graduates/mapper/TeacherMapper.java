package school.hei.graduates.mapper;

import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.TeacherResponse;
import school.hei.graduates.endpoint.rest.model.UpsertTeacher;
import school.hei.graduates.entity.Teacher;

@Component
public class TeacherMapper {

    public Teacher toEntity(UpsertTeacher request) {
        return Teacher.builder()
                .id(request.id())
                .ref(request.ref())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
    }

    public TeacherResponse toResponse(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getRef(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail());
    }
}