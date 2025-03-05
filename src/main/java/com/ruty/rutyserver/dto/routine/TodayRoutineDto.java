package com.ruty.rutyserver.dto.routine;

import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.e.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TodayRoutineDto {
    private Long routineId;
    private String title;
    private Categories category;
    private boolean isDone;

    public static TodayRoutineDto of(Routine routine) {
        return TodayRoutineDto.builder()
                .routineId(routine.getId())
                .title(routine.getTitle())
                .category(routine.getCategory())
                .isDone(routine.getIsDone())
                .build();
    }
}
