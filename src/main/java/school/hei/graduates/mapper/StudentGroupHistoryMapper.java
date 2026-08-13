package school.hei.graduates.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.StudentGroupHistoryResponse;
import school.hei.graduates.entity.StudentGroupHistory;

@Component
@AllArgsConstructor
public class StudentGroupHistoryMapper {

    private final GroupMapper groupMapper;

    public StudentGroupHistoryResponse toResponse(
            StudentGroupHistory history) {

        return new StudentGroupHistoryResponse(
                history.getId(),
                history.getStudent().getId(),
                groupMapper.toResponse(history.getGroup()),
                history.getStartDate(),
                history.getEndDate());
    }
}