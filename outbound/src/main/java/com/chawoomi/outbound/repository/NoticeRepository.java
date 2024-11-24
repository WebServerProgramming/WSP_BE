package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
