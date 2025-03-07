package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.routine_history.RoutineHistoryCountRep;
import com.ruty.rutyserver.dto.routine_history.RoutineHistoryRep;
import com.ruty.rutyserver.dto.routine_history.RoutineHistoryReq;
import com.ruty.rutyserver.security.jwt.JwtUtil;
import com.ruty.rutyserver.service.RoutineHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "루틴 기록 API", description = "피그마[9]: 루틴을 얼마나 이루었는지 확인할 수 있음.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routine/history")
public class RoutineHistoryController {

    private final RoutineHistoryService routineHistoryService;

    /**
     * TODO: 한달간의 루틴 수행결과 보여주기
     * 요청받는값: 루틴 pk, 원하는 날짜
     * 응답값: 루틴 기록 결과 보여주기,
     * */
    @Operation(summary = "루틴 기록 조회하기", description = "조회하고자 하는 루틴 pk, 연도, 달 Json으로 요청해주면 해당 날짜에 맞는 루틴 수행, 미수행 여부 보여줌.")
    @GetMapping("/{routineId}")
    public ResponseEntity<?> getRoutineHistoryInMonth(
            @PathVariable Long routineId,
            @RequestParam(name = "year") Long year,
            @RequestParam(name = "month") Long month) {
        List<RoutineHistoryRep> routineHistoryReps = routineHistoryService.getRoutineHistoryInMonth(routineId, year, month);
        return ResponseEntity.ok(ApiResponse.ok(routineHistoryReps));
    }

    /**
     * TODO: 수행한 루틴 시도횟수, 완료횟수,
     * 요청받는값: 루틴 pk, 원하는 날짜
     * 응답값: 전체 수와 달성한 수, 연속횟수도 보내주기
     * */

    @Operation(summary = "루틴 달성률 확인하기", description = "루틴 전체수, 수행한 루틴수, 연속으로 달성한 루틴 수 보여줌.")
    @GetMapping("/{routineId}/count")
    public ResponseEntity<?> getRoutineHistoryCount(@PathVariable Long routineId) {
        RoutineHistoryCountRep routineHistoryCount = routineHistoryService.getRoutineHistoryCount(routineId);
        return ResponseEntity.ok(ApiResponse.ok(routineHistoryCount));
    }
}
