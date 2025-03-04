package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.dto.routine.RoutineReq;
import com.ruty.rutyserver.entity.e.Category;
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
@Table(name = "routines")
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
    @Column(name = "routines_id")
    private Long id;

    private String title;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER) // 루틴 조회할때 대부분 꼭 조회해야하기에 그냥 eager이 나을듯
    @CollectionTable(name = "routine_weeks", joinColumns = @JoinColumn(name = "routines_id"))
    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장 (또는 ORDINAL 사용 가능)
    private List<Week> weeks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("ONGOING")
    private RoutineProgress routineProgress;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }
    public void setRoutineProgress(RoutineProgress routineProgress) { this.routineProgress = routineProgress; }

    @Builder
    public Routine(String title, String description, List<Week> weeks,
                   LocalDate startDate, LocalDate endDate,
                   Category category, Member member) {
        this.title = title;
        this.description = description;
        this.weeks = weeks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
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
