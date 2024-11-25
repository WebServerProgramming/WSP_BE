package com.chawoomi.outbound.adapter.service;

import com.chawoomi.outbound.adapter.service.dto.NoticeDetail;
import com.chawoomi.outbound.adapter.service.dto.NoticeInfo;
import com.chawoomi.outbound.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeInfo> findAll() {

        return noticeRepository.findAll().stream()
                .map(notice -> new NoticeInfo(notice.getId(), notice.getTitle()))
                .toList();
    }

    public NoticeDetail findDetail(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .map(notice -> new NoticeDetail(notice.getTitle(), notice.getContent()))
                .orElseThrow(() -> new IllegalArgumentException("Notice not found for id: " + noticeId));
    }

}
