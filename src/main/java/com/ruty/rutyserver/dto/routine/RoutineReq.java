package com.ruty.rutyserver.dto.routine;

import com.ruty.rutyserver.entity.e.Categories;
import com.ruty.rutyserver.entity.e.Week;
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
    private Categories category;
    private Long month;
}
