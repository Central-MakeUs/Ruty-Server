package com.ruty.rutyserver.domain.routine;

import com.ruty.rutyserver.domain.improvementGoals.entity.Category;
import com.ruty.rutyserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDto {
    private Long routineId;
    private String title;
    private String description;
    private List<Week> weekList;
    private LocalDate startDate;
    private LocalDate endDate;
    private Category category;
    private Member member;

    public static RoutineDto of(Routines routine) {
        return RoutineDto.builder()
                .routineId(routine.getId())
                .title(routine.getTitle())
                .description(routine.getDescription())
                .weekList(routine.getWeeks())
                .startDate(routine.getStartDate())
                .endDate(routine.getEndDate())
                .category(routine.getCategory())
                .member(routine.getMember())
                .build();
    }
}
