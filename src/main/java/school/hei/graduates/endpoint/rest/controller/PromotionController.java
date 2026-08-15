package school.hei.graduates.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.graduates.endpoint.rest.model.PromotionResponse;
import school.hei.graduates.endpoint.rest.model.UpsertPromotion;
import school.hei.graduates.service.PromotionService;

@RestController
@RequestMapping("/promotions")
@AllArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public List<PromotionResponse> getAll() {
        return promotionService.getAll();
    }

    @GetMapping("/{id}")
    public PromotionResponse getById(@PathVariable UUID id) {
        return promotionService.getById(id);
    }

    @PutMapping
    public PromotionResponse upsert(
            @Valid @RequestBody UpsertPromotion request) {
        return promotionService.upsert(request);
    }
}