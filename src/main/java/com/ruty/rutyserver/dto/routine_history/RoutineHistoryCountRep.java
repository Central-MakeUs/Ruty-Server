package com.ruty.rutyserver.dto.routine_history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineHistoryCountRep {
    private Long totalCount;       // 전체 수행한 루틴 개수
    private Long completedCount;   // 달성한(완료된) 루틴 개수
    private Long streakCount;      // 연속으로 달성한 루틴 개수
}
