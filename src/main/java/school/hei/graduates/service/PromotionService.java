package school.hei.graduates.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.graduates.endpoint.rest.model.PromotionResponse;
import school.hei.graduates.endpoint.rest.model.UpsertPromotion;
import school.hei.graduates.entity.Promotion;
import school.hei.graduates.exception.ResourceNotFoundException;
import school.hei.graduates.mapper.PromotionMapper;
import school.hei.graduates.repository.PromotionRepository;

@Service
@AllArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public List<PromotionResponse> getAll() {
        return promotionRepository.findAll().stream()
                .map(promotionMapper::toResponse)
                .toList();
    }

    public PromotionResponse getById(UUID id) {
        Promotion promotion =
                promotionRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Promotion not found"));

        return promotionMapper.toResponse(promotion);
    }

    public PromotionResponse upsert(UpsertPromotion request) {
        Promotion promotion = promotionMapper.toEntity(request);

        Promotion savedPromotion = promotionRepository.save(promotion);

        return promotionMapper.toResponse(savedPromotion);
    }
}