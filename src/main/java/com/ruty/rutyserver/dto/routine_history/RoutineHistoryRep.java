package com.ruty.rutyserver.dto.routine_history;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistoryRep {
    private LocalDate date;
    private Boolean isDone;

}
