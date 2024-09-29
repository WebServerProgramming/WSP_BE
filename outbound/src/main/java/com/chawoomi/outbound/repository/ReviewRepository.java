package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.JpaReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<JpaReview, Long> {

}
