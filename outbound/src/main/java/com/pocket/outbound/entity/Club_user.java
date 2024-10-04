package com.pocket.outbound.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Club_User")
public class Club_User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long UserId;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club_id;

    @ManyToOne
    @JoinColumn(name = "member_id_id", nullable = false)
    private User member_id;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

}