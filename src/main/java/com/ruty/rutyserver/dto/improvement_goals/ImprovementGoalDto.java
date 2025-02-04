package com.ruty.rutyserver.dto.improvement_goals;

import com.ruty.rutyserver.entity.ImprovementGoal;
import lombok.*;

@Data
@Builder
public class ImprovementGoalDto {
    private Long id;
    private String category;
    private String content;

    public static ImprovementGoalDto of(ImprovementGoal improvementGoal) {
        return ImprovementGoalDto.builder()
                .id(improvementGoal.getId())
                .category(improvementGoal.getCategory().toString())
                .content(improvementGoal.getContent())
                .build();
    }
}
