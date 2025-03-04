package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.routine.CategoryLevelDto;
import com.ruty.rutyserver.dto.routine.RoutineDto;
import com.ruty.rutyserver.dto.routine.RoutineReq;
import com.ruty.rutyserver.dto.routine.TodayRoutineDto;
import com.ruty.rutyserver.security.jwt.JwtUtil;
import com.ruty.rutyserver.service.RoutineService;
import com.ruty.rutyserver.service.RoutineServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "루틴 API", description = "피그마[6-7]")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routine")
public class RoutineController {

    private final RoutineService routineService;

    @Operation(summary = "오늘 루틴 조회")
    @GetMapping("/today")
    public ResponseEntity<?> getTodayRoutine() {
        String email = JwtUtil.getLoginMemberEmail();

        List<TodayRoutineDto> myTodayRoutines = routineService.getMyTodayRoutine(email);
        return ResponseEntity.ok(ApiResponse.ok(myTodayRoutines));
    }

    @Operation(
            summary = "나의 루틴 조회",
            description = "내가 저장해둔 루틴을 전체조회함." +
                    "<br>파라미터가 없으면 나의 루틴 전체, 파라미터를 넣으면 해당 카테고리 전체조회." +
                    "<br>ex: ?category=HOUSE -> 나의 주거 카테고리 조회")
    @GetMapping
    public ResponseEntity<?> getMyRoutines(@RequestParam(defaultValue = "ALL") String category) {
        String email = JwtUtil.getLoginMemberEmail();

        List<RoutineDto> myRoutines = routineService.getMyAllRoutines(email, category);
        return ResponseEntity.ok(ApiResponse.ok(myRoutines));
    }

    @Operation(summary = "나의 카테고리별 레벨 조회", description = "내 카테고리별 레벨을 조회할 수 있음. 레벨식은 기능요구서에 있음.")
    @GetMapping("/category")
    public ResponseEntity<?> getMyCategoryLevels() {
        String email = JwtUtil.getLoginMemberEmail();

        List<CategoryLevelDto> myCategoryLevel = routineService.getMyCategoryLevel(email);
        return ResponseEntity.ok(ApiResponse.ok(myCategoryLevel));
    }

    @Operation(
            summary = "루틴 상태 변경(활성화/비활성화 토글)",
            description = "완료한 루틴을 체크하거나, 이를 취소할 때 요청함." +
                    "<br>요청시 체크되지 않은 루틴을 체크하고, 체크된 루틴은 체크되지 않도록 함." +
                    "<br>체크 여부에 따라 카테고리 레벨을 반환함.")
    @PutMapping("/{routineId}/state")
    public ResponseEntity<?> doneTodayRoutine(@PathVariable(name = "routineId") Long routineId) {
        String email = JwtUtil.getLoginMemberEmail();

        CategoryLevelDto categoryLevelDto = routineService.toggleRoutineStatus(routineId, email);
        return ResponseEntity.ok(ApiResponse.ok(categoryLevelDto));
    }

    @Operation(summary = "새로운 루틴 추가(커스텀)", description = "추천받은 루틴이 아닌 사용자가 스스로 루틴을 만들어 설정함.")
    @PostMapping
    public ResponseEntity<?> saveCustomRoutine(@RequestBody RoutineReq routineReq) {
        String email = JwtUtil.getLoginMemberEmail();

        Long routineId = routineService.saveCustomRoutine(email, routineReq);
        return ResponseEntity.ok(ApiResponse.created(routineId));
    }

    @Operation(summary = "루틴 수정(커스텀, 추천)", description = "추천받은 루틴이든, 직접만든 루틴이든 상관없이 자신에게 있던 루틴을 수정함.")
    @PutMapping("/{routineId}")
    public ResponseEntity<?> updateRoutine(@PathVariable(name = "routineId") Long routineId,
                                           @RequestBody RoutineReq routineReq) {
        String email = JwtUtil.getLoginMemberEmail();

        //service
        Long updateId = routineService.updateRoutine(routineId, routineReq, email);
        return ResponseEntity.ok(ApiResponse.updated(updateId));
    }

    @Operation(summary = "루틴 삭제(커스텀, 추천)", description = "추천받은 루틴이든, 직접만든 루틴이든 선택한 자신의 루틴을 삭제함.")
    @DeleteMapping("/{routineId}")
    public ResponseEntity<?> deleteRoutine(@PathVariable(name = "routineId") Long routineId) {
        String email = JwtUtil.getLoginMemberEmail();

        //service
        routineService.deleteRoutine(routineId);
        return ResponseEntity.ok(ApiResponse.delete(routineId));
    }

    @Operation(
            summary = "루틴 시작/포기하기",
            description = "내 루틴 조회 후, 아래 포기하기 버튼 클릭시 포기함." +
                    "진행중 -> ")
    @PutMapping("/{routineId}/state")
    public ResponseEntity<?> changeRoutineState(@PathVariable(name = "routineId") Long routineId) {
        String email = JwtUtil.getLoginMemberEmail();

        Long updateRoutineProgress = routineService.updateRoutineProgress(routineId);
        return ResponseEntity.ok(ApiResponse.updated(updateRoutineProgress));
    }


}
