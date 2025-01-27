package com.ruty.rutyserver.domain.recommend;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.domain.improvementGoals.entity.Category;
import com.ruty.rutyserver.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommend_routines")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendRoutines extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_routines_id")
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
