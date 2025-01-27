package com.ruty.rutyserver.domain.routine;

import com.ruty.rutyserver.common.ApiResponse;
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
            summary = "루틴 저장(인증토큰 필요)",
            description = "루틴을 저장합니다. 사용자가 새로운 루틴을 만들거나, gpt를 통해 추천받은 루틴을 저장함.")
    @PostMapping
    public ResponseEntity<?> saveRoutine(@RequestBody RoutineReq routineReq,
                                         Principal principal) {
        Long routineId = routineService.saveRoutine(routineReq, principal.getName());
        return ResponseEntity.ok(ApiResponse.created(routineId));
    }

    @Operation(
            summary = "루틴 전체조회",
            description = "루틴을 모두 조회합니다.(개발용)")
    @GetMapping("/result")
    public ResponseEntity<?> getAllRoutine() {
        List<RoutineDto> routineDtos = routineService.getAllRoutines();
        return ResponseEntity.ok(ApiResponse.ok(routineDtos));
    }
}
