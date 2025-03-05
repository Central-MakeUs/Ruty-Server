package com.ruty.rutyserver.dto.routine;

import com.ruty.rutyserver.entity.CategoryLevel;
import com.ruty.rutyserver.entity.e.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class CategoryLevelInfoDto {
    private Long id;
    private Categories category;
    private Long level;
    private Long totalPoints;
    private Long memberId;

    public static CategoryLevelInfoDto of(CategoryLevel level) {
        return CategoryLevelInfoDto.builder()
                .id(level.getId())
                .category(level.getCategory())
                .level(level.getLevel())
                .totalPoints(level.getTotalPoints())
                .memberId(level.getMember().getId())
                .build();
    }
}
