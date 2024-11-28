package com.chawoomi.outbound.entity;

import com.chawoomi.core.exception.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Club_User")
public class ClubUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    public void assignClubAndUser(Club club, User user) {
        if (club == null || user == null) {
            throw new IllegalArgumentException("Club과 User는 null일 수 없습니다.");
        }
        this.club = club;
        this.user = user;
    }

    public void assignRole(Long roleId) {
        if (roleId == null || roleId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 Role ID입니다.");
        }
        this.roleId = roleId;
    }

}