package com.chawoomi.outbound.adapter.service;

import com.chawoomi.outbound.adapter.service.dto.ClubInfo;
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
}
