package com.chawoomi.inbounds.calendar.presentation;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.VoteService;
import com.chawoomi.outbound.adapter.service.dto.CalendarEntry;
import com.chawoomi.outbound.aop.UserResolver;
import com.chawoomi.outbound.entity.User;
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
@RequestMapping("/v1/api/calendar")
@Tag(name = "Calendar API", description = "달력 관련 API")
public class CalendarController {

    private final VoteService voteService;

    @Operation(summary = "전체 달력 조회", description = "모든 투표의 시작 날짜와 종료 날짜를 기준으로 전체 달력을 반환합니다.")
    @GetMapping("/all")
    public ApplicationResponse<List<CalendarEntry>> getAllCalendar(
            @UserResolver User user
    ) {
        List<CalendarEntry> calendarDates = voteService.getAllCalendar(user);
        return ApplicationResponse.ok(calendarDates);
    }

    @Operation(summary = "해당 동아리 달력 조회", description = "특정 클럽의 투표 시작 날짜와 종료 날짜를 기준으로 달력을 반환합니다.")
    @GetMapping("/{clubId}/single")
    public ApplicationResponse<List<CalendarEntry>> getSingleCalendar(
            @UserResolver User user,
            @PathVariable Long clubId
    ) {
        List<CalendarEntry> calendarDates = voteService.getSingleCalendar(user, clubId);
        return ApplicationResponse.ok(calendarDates);
    }
}
