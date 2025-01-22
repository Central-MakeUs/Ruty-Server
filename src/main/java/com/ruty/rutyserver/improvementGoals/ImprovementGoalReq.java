package com.ruty.rutyserver.improvementGoals;

import lombok.*;

@Data
@Builder
public class ImprovementGoalReq {
    private Long id;
    private String category;
    private String content;

    public static ImprovementGoalReq of(ImprovementGoals improvementGoals) {
        return ImprovementGoalReq.builder()
                .id(improvementGoals.getId())
                .category(improvementGoals.getCategory().toString())
                .content(improvementGoals.getContent())
                .build();
    }
}
