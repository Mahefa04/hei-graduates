package school.hei.graduates.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.StudentGroupHistoryResponse;
import school.hei.graduates.endpoint.rest.model.UpsertStudentGroupHistory;
import school.hei.graduates.entity.Student;
import school.hei.graduates.entity.Group;
import school.hei.graduates.entity.StudentGroupHistory;

@Component
@AllArgsConstructor
public class StudentGroupHistoryMapper {

    private final StudentMapper studentMapper;
    private final GroupMapper groupMapper;

    public StudentGroupHistory toEntity(
            UpsertStudentGroupHistory request,
            Student student,
            Group group) {

        return StudentGroupHistory.builder()
                .id(request.id())
                .student(student)
                .group(group)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public StudentGroupHistoryResponse toResponse(
            StudentGroupHistory history) {

        return new StudentGroupHistoryResponse(
                history.getId(),
                studentMapper.toResponse(history.getStudent()),
                groupMapper.toResponse(history.getGroup()),
                history.getStartDate(),
                history.getEndDate());
    }
}