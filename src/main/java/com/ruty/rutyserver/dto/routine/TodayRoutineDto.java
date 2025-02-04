package com.ruty.rutyserver.dto.routine;

import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.e.Category;
import com.ruty.rutyserver.entity.e.Week;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TodayRoutineDto {
    private Long routineId;
    private String title;
    private Category category;
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
