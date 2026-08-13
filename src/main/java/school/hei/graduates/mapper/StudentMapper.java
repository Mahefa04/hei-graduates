package school.hei.graduates.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.StudentResponse;
import school.hei.graduates.endpoint.rest.model.UpsertStudent;
import school.hei.graduates.entity.Promotion;
import school.hei.graduates.entity.Student;

@Component
@AllArgsConstructor
public class StudentMapper {

    private final PromotionMapper promotionMapper;

    public Student toEntity(
            UpsertStudent request,
            Promotion promotion) {

        return Student.builder()
                .id(request.id())
                .ref(request.ref())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .promotion(promotion)
                .build();
    }

    public StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getRef(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                promotionMapper.toResponse(student.getPromotion()));
    }
}