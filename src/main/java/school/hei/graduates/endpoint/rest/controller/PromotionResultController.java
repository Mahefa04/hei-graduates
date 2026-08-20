package school.hei.graduates.endpoint.rest.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.graduates.endpoint.rest.model.SemesterResultResponse;
import school.hei.graduates.service.PromotionResultService;

@RestController
@RequestMapping("/promotion-results")
@AllArgsConstructor
public class PromotionResultController {

    private final PromotionResultService promotionResultService;

    @GetMapping("/promotion/{promotionId}/student/{studentId}")
    public List<SemesterResultResponse> getStudentResults(
            @PathVariable UUID promotionId,
            @PathVariable UUID studentId) {

        return promotionResultService.getStudentResults(
                promotionId,
                studentId);
    }

    @GetMapping("/promotion/{promotionId}/student/{studentId}/average")
    public BigDecimal getPromotionAverage(
            @PathVariable UUID promotionId,
            @PathVariable UUID studentId) {

        return promotionResultService.calculatePromotionAverage(
                promotionId,
                studentId);
    }
}