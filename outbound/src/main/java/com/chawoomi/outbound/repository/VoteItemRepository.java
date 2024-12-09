package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.Vote;
import com.chawoomi.outbound.entity.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {

    List<VoteItem> findByVote(Vote vote);
}