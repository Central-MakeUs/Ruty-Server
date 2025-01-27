package com.ruty.rutyserver.domain.routine;

import com.ruty.rutyserver.domain.improvementGoals.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineReq {
    private String title;
    private String description;
    private List<Week> weekList;
    private Category category;
    private Long month;
}
