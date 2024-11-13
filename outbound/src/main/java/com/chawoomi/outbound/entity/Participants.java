package com.chawoomi.outbound.entity;

import com.chawoomi.core.exception.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Participants")

public class Participants extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule_id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User member_id;

    @Column(name = "status", nullable = false)
    private Long status;


}
