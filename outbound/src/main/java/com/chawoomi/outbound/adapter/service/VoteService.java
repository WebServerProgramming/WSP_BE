package com.chawoomi.outbound.adapter.service;

import com.chawoomi.core.exception.common.ApplicationResponse;
import com.chawoomi.outbound.adapter.service.dto.CalendarEntry;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
     * 전체 투표의 시작일과 종료일을 반환합니다.
     *
     * @param user 사용자 정보
     * @return 날짜와 해당 날짜 타입의 리스트
     */
    public List<CalendarEntry> getAllCalendar(User user) {
        List<Vote> votes = voteRepository.findAll();
        return extractStartAndEndDatesFromVotes(votes);
    }

    /**
     * 특정 클럽의 투표 시작일과 종료일을 반환합니다.
     *
     * @param user   사용자 정보
     * @param clubId 클럽 ID
     * @return 날짜와 해당 날짜 타입의 리스트
     */
    public List<CalendarEntry> getSingleCalendar(User user, Long clubId) {
        List<Vote> votes = voteRepository.findByClubId(clubId);
        return extractStartAndEndDatesFromVotes(votes);
    }

    /**
     * 투표 리스트에서 시작일과 종료일을 추출합니다.
     *
     * @param votes 투표 리스트
     * @return 날짜와 날짜 타입의 리스트
     */
    private List<CalendarEntry> extractStartAndEndDatesFromVotes(List<Vote> votes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<CalendarEntry> calendarEntries = new ArrayList<>();

        for (Vote vote : votes) {
            // 시작일 추가
            calendarEntries.add(new CalendarEntry(
                    LocalDate.parse(vote.getStartDate(), formatter).format(formatter),
                    "Start"
            ));

            // 종료일 추가
            calendarEntries.add(new CalendarEntry(
                    LocalDate.parse(vote.getEndDate(), formatter).format(formatter),
                    "End"
            ));
        }

        // 날짜를 정렬
        calendarEntries.sort(Comparator.comparing(CalendarEntry::getDate));
        return calendarEntries;
    }

}
