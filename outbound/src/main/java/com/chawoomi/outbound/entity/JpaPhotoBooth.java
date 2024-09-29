package com.chawoomi.outbound.entity;

import com.chawoomi.domain.entity.photobooth.PhotoBooth;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "PHOTOBOOTH")
public class JpaPhotoBooth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photoBooth_id")
    private Long id;

    @Embedded
    private PhotoBooth photoBooth;

}
