package com.chawoomi.outbound.entity;

import com.chawoomi.core.exception.common.BaseEntity;
import com.chawoomi.domain.dto.OidcDecodePayload;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "subId", nullable = false)
    private String subId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "picture", nullable = true)
    private String picture;

    public static User createUser(OidcDecodePayload oidcDecodePayload) {

        User user = new User();
        user.subId = oidcDecodePayload.sub();
        user.name = oidcDecodePayload.nickname();
        user.email = oidcDecodePayload.email();
        user.picture = oidcDecodePayload.picture();
        return user;
    }

}