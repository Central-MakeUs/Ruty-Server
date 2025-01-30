package com.ruty.rutyserver.domain.routine;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.domain.improvementGoals.entity.Category;
import com.ruty.rutyserver.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.boot.model.process.internal.ScanningCoordinator;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "routines")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routines extends BaseEntity {
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
    public Routines(String title, String description, List<Week> weeks,
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

    public static Routines toEntity(RoutineReq req, Member member) {
        return Routines.builder()
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
