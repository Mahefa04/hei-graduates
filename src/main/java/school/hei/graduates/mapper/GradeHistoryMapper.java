package school.hei.graduates.mapper;

import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.GradeHistoryResponse;
import school.hei.graduates.entity.GradeHistory;

@Component
public class GradeHistoryMapper {

  public GradeHistoryResponse toResponse(GradeHistory history) {

    return new GradeHistoryResponse(
        history.getId(),
        history.getGrade().getId(),
        history.getOldValue(),
        history.getNewValue(),
        history.getReason(),
        history.getModifiedBy(),
        history.getModifiedAt());
  }
}
