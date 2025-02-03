package com.ruty.rutyserver.dto.recommend;

import com.ruty.rutyserver.entity.e.Category;
import com.ruty.rutyserver.entity.RecommendRoutine;
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

    public static RecommendRoutineDto of(RecommendRoutine recommendRoutines) {
        return RecommendRoutineDto.builder()
                .id(recommendRoutines.getId())
                .title(recommendRoutines.getTitle())
                .description(recommendRoutines.getDescription())
                .category(recommendRoutines.getCategory())
                .build();
    }
}
