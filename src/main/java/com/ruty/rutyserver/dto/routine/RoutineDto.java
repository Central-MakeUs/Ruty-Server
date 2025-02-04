package com.ruty.rutyserver.dto.routine;

import com.ruty.rutyserver.dto.member.MemberDto;
import com.ruty.rutyserver.dto.member.MemberInfoDto;
import com.ruty.rutyserver.entity.e.Category;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.e.Week;
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
    private MemberInfoDto memberInfoDto;

    public static RoutineDto of(Routine routine) {
        MemberInfoDto member = MemberInfoDto.of(routine.getMember());
        return RoutineDto.builder()
                .routineId(routine.getId())
                .title(routine.getTitle())
                .description(routine.getDescription())
                .weekList(routine.getWeeks())
                .startDate(routine.getStartDate())
                .endDate(routine.getEndDate())
                .category(routine.getCategory())
                .memberInfoDto(member)
                .build();
    }
}
