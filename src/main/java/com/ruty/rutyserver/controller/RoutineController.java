package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.routine.RoutineDto;
import com.ruty.rutyserver.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
