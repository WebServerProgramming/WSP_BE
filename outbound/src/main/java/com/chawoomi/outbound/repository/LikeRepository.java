package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.JpaLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<JpaLike, Long> {
}
