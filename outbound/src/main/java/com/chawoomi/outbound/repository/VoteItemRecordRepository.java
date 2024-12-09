package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.User;
import com.chawoomi.outbound.entity.Vote;
import com.chawoomi.outbound.entity.VoteItemRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteItemRecordRepository extends JpaRepository<VoteItemRecord, Long> {

    Optional<VoteItemRecord> findByVoteAndUser(Vote vote, User user);
}