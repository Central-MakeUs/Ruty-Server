package com.ruty.rutyserver.domain.improvementGoals.entity;

import com.ruty.rutyserver.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "improvement_goals")
@Getter
@NoArgsConstructor
public class ImprovementGoals extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "improvement_goal_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String content;

    @Builder
    public ImprovementGoals(Category category, String content) {
        this.category = category;
        this.content = content;
    }
}
