package com.ruty.rutyserver.dto.routine_history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class RoutineHistoryReq {
    private Long routineId;
    private Long year;
    private Long month;
}
