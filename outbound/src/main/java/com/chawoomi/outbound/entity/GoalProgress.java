package com.chawoomi.outbound.entity;

import com.chawoomi.core.exception.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Goal_Progress")
public class GoalProgress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long progress_id;

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal_id;

    @ManyToOne
    @JoinColumn(name = "club_user_id", nullable = false)
    private ClubUser club_user_id;

    @Column(name = "progress", nullable = false)
    private Long progress;
}