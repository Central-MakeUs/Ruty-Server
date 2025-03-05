package com.ruty.rutyserver.dto.routine;

import com.ruty.rutyserver.entity.CategoryLevel;
import com.ruty.rutyserver.entity.e.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoryLevelDto {
    private Categories category;
    private Long level;
    private Long totalPoints;

    public static CategoryLevelDto of(CategoryLevel level) {
        return CategoryLevelDto.builder()
                .category(level.getCategory())
                .level(level.getLevel())
                .totalPoints(level.getTotalPoints())
                .build();
    }
}
