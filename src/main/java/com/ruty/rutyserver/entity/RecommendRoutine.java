package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.entity.e.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommend_routines")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendRoutine extends BaseEntity {
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

    @Builder
    public RecommendRoutine(String title, String description,
                            Category category, Member member) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.member = member;
    }
}
