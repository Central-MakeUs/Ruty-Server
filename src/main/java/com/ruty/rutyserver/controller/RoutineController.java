package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.routine.CategoryLevelDto;
import com.ruty.rutyserver.dto.routine.RoutineDto;
import com.ruty.rutyserver.dto.routine.TodayRoutineDto;
import com.ruty.rutyserver.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "루틴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routine")
public class RoutineController {

    private final RoutineService routineService;

    @Operation(
            summary = "루틴 전체조회",
            description = "루틴을 모두 조회합니다.(개발용)")
    @GetMapping("/result")
    public ResponseEntity<?> getAllRoutine() {
        List<RoutineDto> routineDtos = routineService.getAllRoutines();
        return ResponseEntity.ok(ApiResponse.ok(routineDtos));
    }

    @Operation(summary = "오늘 나의 루틴 조회하기")
    @GetMapping("/today")
    public ResponseEntity<?> getTodayRoutine(Principal principal) {
        List<TodayRoutineDto> myTodayRoutines = routineService.getMyTodayRoutine(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(myTodayRoutines));
    }

    @Operation(summary = "나의 카테고리별 레벨 확인하기")
    @GetMapping("/level")
    public ResponseEntity<?> getMyCategoryLevels(Principal principal) {
        List<CategoryLevelDto> myCategoryLevel = routineService.getMyCategoryLevel(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(myCategoryLevel));
    }

    @Operation(summary = "루틴 상태 변경 (활성화/비활성화 토글)")
    @PutMapping("/toggle/{routineId}")
    public ResponseEntity<?> doneTodayRoutine(
            @PathVariable(name = "routineId") Long routineId,
            Principal principal) {
        CategoryLevelDto categoryLevelDto = routineService.toggleRoutineStatus(routineId, principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(categoryLevelDto));
    }

}
