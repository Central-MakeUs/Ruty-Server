package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.dto.routine.RoutineReq;
import com.ruty.rutyserver.entity.e.Categories;
import com.ruty.rutyserver.entity.e.RoutineProgress;
import com.ruty.rutyserver.entity.e.Week;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.List;

import static com.ruty.rutyserver.entity.e.RoutineProgress.ONGOING;

@Entity
@Table(name = "routines")  // 테이블 이름에 백틱 추가
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Routine extends BaseEntity {

    @PrePersist
    public void prePersist() {
        this.routineProgress = this.routineProgress == null ? ONGOING : this.routineProgress;
        this.isDone = this.isDone == null ? false : this.isDone;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")  // 컬럼 이름에 백틱 추가
    private Long id;

    private String title;

    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "routine_weeks", joinColumns = @JoinColumn(name = "routines_id"))
    @Enumerated(EnumType.STRING)
    private List<Week> weeks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categories category;

    @Column(name = "`start_date`")
    private LocalDate startDate;

    @Column(name = "`end_date`")
    private LocalDate endDate;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'ONGOING'")
    private RoutineProgress routineProgress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")  // 외래 키에 백틱 추가
    private Member member;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineHistory> routineHistoryList;

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }
    public void setRoutineProgress(RoutineProgress routineProgress) { this.routineProgress = routineProgress; }

    @Builder
    public Routine(String title, String description, List<Week> weeks,
                   LocalDate startDate, LocalDate endDate, Boolean isDone,
                   Categories category, Member member) {
        this.title = title;
        this.description = description;
        this.weeks = weeks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.isDone = isDone;
        this.member = member;
    }

    public static Routine toEntity(RoutineReq req, Member member) {
        return Routine.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .weeks(req.getWeekList())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(req.getMonth()))
                .category(req.getCategory())
                .member(member)
                .build();
    }

    // 수정중..
    public void updateRoutine(RoutineReq updateRoutine) {
        this.title = updateRoutine.getTitle();
        this.description = updateRoutine.getDescription();
        this.weeks = updateRoutine.getWeekList();
        // 시작일은 고정. 종료일만 변경됨.
        this.endDate = startDate.plusMonths(updateRoutine.getMonth());
        this.category = updateRoutine.getCategory();
    }
}
