package com.chawoomi.inbounds.vote.presentation;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.VoteService;
import com.chawoomi.outbound.adapter.service.dto.VoteStatus;
import com.chawoomi.outbound.aop.UserResolver;
import com.chawoomi.outbound.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/vote")
@Tag(name = "Vote API", description = "투표 관련 API")
public class VoteController {

    private final VoteService voteService;

    /**
     * 투표 현황 조회 - 명단까지 리스트로 전달 필요
     */
    @Operation(
            summary = "투표 현황 조회",
            description = "특정 클럽의 투표 현황을 조회합니다. 투표 명단까지 포함된 리스트를 반환합니다."
    )
    @GetMapping("/{clubId}")
    public ApplicationResponse<List<VoteStatus>> getVoteStatus(
            @Parameter(description = "클럽 ID", required = true) @PathVariable Long clubId
    ) {
        return voteService.getVoteStatus(clubId);
    }

    /**
     * 투표 참여
     */
    @Operation(
            summary = "투표 참여",
            description = "특정 투표 항목에 참여합니다."
    )
    @PostMapping(value = "/{voteId}/{itemId}/attend")
    public ApplicationResponse<Long> attendVote(
            @Parameter(description = "투표 ID", required = true) @PathVariable Long voteId,
            @Parameter(description = "투표 항목 ID", required = true) @PathVariable Long itemId,
            @Parameter(hidden = true) @UserResolver User user
    ) {
        return voteService.attendVote(voteId, itemId, user);
    }
}
