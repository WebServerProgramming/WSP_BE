package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByClubId(Long clubId);
}