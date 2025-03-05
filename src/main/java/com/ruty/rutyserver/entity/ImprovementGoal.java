package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.entity.e.Categories;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "improvement_goals")
@Getter
@NoArgsConstructor
public class ImprovementGoal extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "improvement_goal_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categories category;

    @Column(nullable = false)
    private String content;

    @Builder
    public ImprovementGoal(Categories category, String content) {
        this.category = category;
        this.content = content;
    }
}
