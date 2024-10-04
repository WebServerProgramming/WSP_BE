package com.pocket.outbound.entity;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Goal_Progress")
public class Goal_Progress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long progress_id;

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Club_user user_id;

    @Column(name = "progress", nullable = false)
    private Long progress;
}