package com.pocket.outbound.entity;

import com.chawoomi.core.exception.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Goal")
public class Goal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "due_date", nullable = false)
    private String dueDate;

    @Column(name = "status", nullable = false)
    private Long status;

    @Column(name = "progress", nullable = false)
    private Long progress;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User member_id;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;
}