package com.ruty.rutyserver.domain.recommend.dto;

import com.ruty.rutyserver.domain.improvementGoals.entity.Category;
import com.ruty.rutyserver.domain.recommend.RecommendRoutines;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RecommendRoutineDto {
    private Long id;
    private String title;
    private String description;
    private Category category;

    public static RecommendRoutineDto of(RecommendRoutines recommendRoutines) {
        return RecommendRoutineDto.builder()
                .id(recommendRoutines.getId())
                .title(recommendRoutines.getTitle())
                .description(recommendRoutines.getDescription())
                .category(recommendRoutines.getCategory())
                .build();
    }
}
