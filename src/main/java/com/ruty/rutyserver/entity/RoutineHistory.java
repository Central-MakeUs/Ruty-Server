package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "routine_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineHistory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Boolean isDone;

    @Builder
    public RoutineHistory (Member member, Routine routine, LocalDate date, Boolean isDone) {
        this.member = member;
        this.routine = routine;
        this.date = date;
        this.isDone = isDone;
    }

}
