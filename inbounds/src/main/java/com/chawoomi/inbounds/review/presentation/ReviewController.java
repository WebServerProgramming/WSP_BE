package com.chawoomi.inbounds.review.presentation;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.ReviewService;
import com.chawoomi.outbound.adapter.service.dto.ClubInfo;
import com.chawoomi.outbound.adapter.service.dto.ReviewSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
