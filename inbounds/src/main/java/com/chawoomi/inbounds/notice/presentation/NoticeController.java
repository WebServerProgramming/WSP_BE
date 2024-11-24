package com.chawoomi.inbounds.notice.presentation;


import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.NoticeService;
import com.chawoomi.outbound.adapter.service.dto.NoticeInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/notice")
@Tag(name = "Notice API", description = "공지 관련 API")
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지 목록 조회", description = "전체 공지 목록을 조회합니다.")
    @GetMapping()
    public ApplicationResponse<List<NoticeInfo>> getTotalLists() {
        final List<NoticeInfo> all = noticeService.findAll();
        return ApplicationResponse.ok(all);
    }
}
