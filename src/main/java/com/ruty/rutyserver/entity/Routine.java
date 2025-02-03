package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.dto.routine.RoutineReq;
import com.ruty.rutyserver.entity.e.Category;
import com.ruty.rutyserver.entity.e.Week;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "routines")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine extends BaseEntity {
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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
}
