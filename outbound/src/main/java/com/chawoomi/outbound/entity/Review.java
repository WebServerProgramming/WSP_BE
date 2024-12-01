package com.chawoomi.outbound.entity;

import com.chawoomi.core.exception.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private Integer rating;
    private String content;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Review(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    public void assignClubAndUser(Club club, User user) {
        if (club == null || user == null) {
            throw new IllegalArgumentException("Club과 User는 null일 수 없습니다.");
        }
        this.club = club;
        this.user = user;
    }
}
