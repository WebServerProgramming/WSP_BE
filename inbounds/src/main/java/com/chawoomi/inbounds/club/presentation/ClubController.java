package com.chawoomi.inbounds.club.presentation;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.ClubService;
import com.chawoomi.outbound.adapter.service.dto.AllClubInfo;
import com.chawoomi.outbound.adapter.service.dto.ClubInfo;
import com.chawoomi.outbound.adapter.service.dto.ClubMember;
import com.chawoomi.outbound.aop.UserResolver;
import com.chawoomi.outbound.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/club")
@Tag(name = "Club API", description = "동아리 관련 API")
public class ClubController {

    private final ClubService clubService;

    @Operation(summary = "전체 동아리 목록 조회", description = "전체 동아리 목록을 조회합니다.")
    @GetMapping()
    public ApplicationResponse<List<AllClubInfo>> getTotalLists() {
        final List<AllClubInfo> all = clubService.findAll();
        return ApplicationResponse.ok(all);
    }

    @Operation(summary = "본인이 속한 동아리 목록 조회", description = "본인 동아리 목록을 조회합니다.")
    @GetMapping("/my")
    public ApplicationResponse<List<ClubInfo>> getMyLists(
            @UserResolver User user
    ) {
        final List<ClubInfo> all = clubService.findMyClub(user.getId());
        return ApplicationResponse.ok(all);
    }

    @Operation(summary = "동아리 가입", description = "동아리 가입")
    @PostMapping("/{clubId}/join")
    public ApplicationResponse<String> joinClub(
            @UserResolver User user,
            @PathVariable Long clubId
    ) {

        clubService.joinClub(user.getId(), clubId);
        return ApplicationResponse.ok("동아리에 가입하였습니다.");
    }

    @Operation(summary = "동아리 세부 명단 조회", description = "특정 동아리의 회원 명단을 조회합니다.")
    @GetMapping("/{clubId}/people")
    public ApplicationResponse<List<ClubMember>> findAllPeople(
            @PathVariable Long clubId
    ) {
        final List<ClubMember> members = clubService.findMembers(clubId);
        return ApplicationResponse.ok(members);
    }
}
