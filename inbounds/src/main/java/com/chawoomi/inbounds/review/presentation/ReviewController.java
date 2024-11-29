package com.chawoomi.inbounds.review.presentation;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.ReviewService;
import com.chawoomi.outbound.adapter.service.dto.ReviewSaveDto;
import com.chawoomi.outbound.adapter.service.dto.ReviewSummaryDto;
import com.chawoomi.outbound.aop.UserResolver;
import com.chawoomi.outbound.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/review")
@Tag(name = "Review API", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "동아리 목록 조회", description = "전체 동아리 목록을 조회합니다.")
    @GetMapping("/{clubId}")
    public ApplicationResponse<ReviewSummaryDto> getTotalLists(
            @PathVariable Long clubId) {
        final ReviewSummaryDto all = reviewService.findAll(clubId);
        return ApplicationResponse.ok(all);
    }

    @Operation(summary = "리뷰 저장", description = "리뷰를 저장합니다.")
    @GetMapping("/{clubId}/save")
    public ApplicationResponse<String> saveReview(
            @UserResolver User user,
            @PathVariable Long clubId,
            @RequestBody ReviewSaveDto request) {
        reviewService.saveReview(user.getId(), clubId, request.getRating(), request.getContent());
        return ApplicationResponse.ok("리뷰가 저장되었습니다.");
    }
}
