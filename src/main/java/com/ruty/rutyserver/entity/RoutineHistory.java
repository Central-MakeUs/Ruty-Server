package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Entity
@Table(name = "recommend_routines")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineHistory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member user;

    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean isDone;
}
