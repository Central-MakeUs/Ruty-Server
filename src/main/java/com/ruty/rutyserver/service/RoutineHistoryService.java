package com.ruty.rutyserver.service;

import com.ruty.rutyserver.dto.routine_history.RoutineHistoryCountRep;
import com.ruty.rutyserver.dto.routine_history.RoutineHistoryRep;
import com.ruty.rutyserver.dto.routine_history.RoutineHistoryReq;

import java.util.List;

public interface RoutineHistoryService {
    List<RoutineHistoryRep> getRoutineHistoryInMonth(Long routineId, Long year, Long month);

    RoutineHistoryCountRep getRoutineHistoryCount(Long routineId);
}
