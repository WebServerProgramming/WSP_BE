package com.chawoomi.outbound.adapter.service;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.dto.VoteStatus;
import com.chawoomi.outbound.entity.User;
import com.chawoomi.outbound.entity.Vote;
import com.chawoomi.outbound.entity.VoteItem;
import com.chawoomi.outbound.entity.VoteItemRecord;
import com.chawoomi.outbound.repository.VoteItemRecordRepository;
import com.chawoomi.outbound.repository.VoteItemRepository;
import com.chawoomi.outbound.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteItemRepository voteItemRepository;
    private final VoteItemRecordRepository voteItemRecordRepository;

    /**
     * 투표 현황 조회
     *
     * @param clubId 클럽 ID
     * @return 투표 현황
     */
    public ApplicationResponse<List<VoteStatus>> getVoteStatus(Long clubId) {

        // 클럽 ID에 해당하는 투표 조회
        List<Vote> votes = voteRepository.findByClubId(clubId);

        // 투표 및 항목 리스트 생성
        List<VoteStatus> voteStatuses = votes.stream().map(vote -> {
            List<VoteItem> items = voteItemRepository.findByVote(vote);
            return new VoteStatus(vote, items);
        }).toList();

        return ApplicationResponse.ok(voteStatuses);
    }

    /**
     * 투표 참여
     *
     * @param voteId 투표 ID
     * @param itemId 투표 항목 ID
     * @return 생성된 기록 ID
     */
    public ApplicationResponse<Long> attendVote(Long voteId, Long itemId, User user) {
        // 투표 및 항목 조회
        Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new IllegalArgumentException("투표가 존재하지 않습니다."));
        VoteItem voteItem = voteItemRepository.findById(itemId)
                .filter(item -> item.getVote().getId().equals(voteId))
                .orElseThrow(() -> new IllegalArgumentException("투표 항목이 잘못되었습니다."));

        // 중복 참여 여부 확인
        Optional<VoteItemRecord> existingRecord = voteItemRecordRepository.findByVoteAndUser(vote, user);
        if (existingRecord.isPresent()) {
            throw new IllegalArgumentException("이미 투표에 참여하셨습니다.");
        }

        // 투표 기록 생성
        VoteItemRecord record = new VoteItemRecord(vote, voteItem, user);
        voteItemRecordRepository.save(record);

        return ApplicationResponse.ok(record.getId());
    }

    /**
     * 전체 달력 조회
     *
     * @param user 사용자 정보
     * @return 각 월의 날짜 리스트
     */
    public List<String> getAllCalendar(User user) {
        List<Vote> votes = voteRepository.findAll();
        return extractDatesFromVotes(votes);
    }

    /**
     * 특정 클럽의 달력 조회
     *
     * @param user   사용자 정보
     * @param clubId 클럽 ID
     * @return 해당 클럽의 각 월의 날짜 리스트
     */
    public List<String> getSingleCalendar(User user, Long clubId) {
        List<Vote> votes = voteRepository.findByClubId(clubId);
        return extractDatesFromVotes(votes);
    }

    /**
     * 투표 리스트에서 시작 날짜와 종료 날짜를 기준으로 날짜를 추출합니다.
     *
     * @param votes 투표 리스트
     * @return 날짜 리스트 (yyyy-MM-dd)
     */
    private List<String> extractDatesFromVotes(List<Vote> votes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Set<LocalDate> uniqueDates = new HashSet<>();

        for (Vote vote : votes) {
            LocalDate startDate = LocalDate.parse(vote.getStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(vote.getEndDate(), formatter);

            // 시작 날짜부터 종료 날짜까지의 날짜를 추가
            while (!startDate.isAfter(endDate)) {
                uniqueDates.add(startDate);
                startDate = startDate.plusDays(1);
            }
        }

        // 날짜를 정렬하고 문자열로 변환
        return uniqueDates.stream()
                .sorted()
                .map(date -> date.format(formatter))
                .toList();
    }
}
