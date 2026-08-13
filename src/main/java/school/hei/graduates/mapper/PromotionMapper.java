package school.hei.graduates.mapper;

import org.springframework.stereotype.Component;
import school.hei.graduates.endpoint.rest.model.PromotionResponse;
import school.hei.graduates.endpoint.rest.model.UpsertPromotion;
import school.hei.graduates.entity.Promotion;

@Component
public class PromotionMapper {

    public Promotion toEntity(UpsertPromotion request) {
        return Promotion.builder()
                .id(request.id())
                .ref(request.ref())
                .startYear(request.startYear())
                .build();
    }

    public PromotionResponse toResponse(Promotion promotion) {
        return new PromotionResponse(
                promotion.getId(),
                promotion.getRef(),
                promotion.getStartYear());
    }
}