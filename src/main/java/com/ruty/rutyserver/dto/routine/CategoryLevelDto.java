package com.ruty.rutyserver.dto.routine;

import com.ruty.rutyserver.entity.Level;
import com.ruty.rutyserver.entity.e.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoryLevelDto {
    private Category category;
    private Long level;
    private Long totalPoints;

    public static CategoryLevelDto of(Level level) {
        return CategoryLevelDto.builder()
                .category(level.getCategory())
                .level(level.getLevel())
                .totalPoints(level.getTotalPoints())
                .build();
    }
}
