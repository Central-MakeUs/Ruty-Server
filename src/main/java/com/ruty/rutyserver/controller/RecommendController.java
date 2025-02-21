package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.security.jwt.JwtUtil;
import com.ruty.rutyserver.service.RecommendServiceImpl;
import com.ruty.rutyserver.dto.recommend.RecommendRoutineDto;
import com.ruty.rutyserver.dto.routine.RoutineReq;
import com.ruty.rutyserver.service.RoutineServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "추천루틴 API", description = "피그마[4-5]")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendServiceImpl recommendService;
    private final RoutineServiceImpl routineService;
    @Operation(
            summary = "chatgpt에게 추천 루틴 요청",
            description = "파라미터에 개선하고 싶은 점을 반점으로 구분해서 넣어주면 됨.<br>" +
                    "ex: /api/recommend?안정적인 주거환경 만들기, 외로움과 고립감 해소하기")
    @GetMapping
    public ResponseEntity<?> chat(@RequestParam(name = "prompt") String prompt){
        String email = JwtUtil.getLoginMemberEmail();
        List<RecommendRoutineDto> gptResponse = recommendService.getGptResponse(prompt, email);
        return ResponseEntity.ok(ApiResponse.created(gptResponse));
    }

    @Operation(
            summary = "추천받은 루틴 저장",
            description = "gpt를 통해 추천받은 루틴을 회원 자신의 루틴에 추가함." +
                    "<br>저장한 추천 루틴은, 추천루틴 전체 조회(api/recommend/my)시, 조회안됨." +
                    "<br><br>PathVariable: 추천받은 루틴의 pk값")
    @PostMapping("/{recommendId}")
    public ResponseEntity<?> saveRecommendRoutine(
            @PathVariable(name = "recommendId") Long recommendId,
            @RequestBody RoutineReq routineReq) {
        String email = JwtUtil.getLoginMemberEmail();

        Long routineId = routineService.saveRoutine(recommendId, routineReq, email);
        return ResponseEntity.ok(ApiResponse.created(routineId));
    }

    @Operation(summary = "나의 추천받은 루틴 전체 조회", description = "내가 추천받았던 루틴을 전체 조회함.")
    @GetMapping("/my")
    public ResponseEntity<?> getAllRecommend() {
        String email = JwtUtil.getLoginMemberEmail();

        List<RecommendRoutineDto> recommendRoutineDtos = recommendService.getMyAllRecommends(email);
        return ResponseEntity.ok(ApiResponse.ok(recommendRoutineDtos));
    }

    @Operation(summary = "나의 추천받은 루틴 단일 조회", description = "내가 추천받았던 루틴 하나만 조회함. 이때 pk값 pathvariable로 이용함.")
    @GetMapping("/my/{recommendId}")
    public ResponseEntity<?> getAllRecommend(@PathVariable(name = "recommendId") Long recommendId) {
        String email = JwtUtil.getLoginMemberEmail();

        RecommendRoutineDto recommendRoutineDto = recommendService.getMyRecommend(email, recommendId);
        return ResponseEntity.ok(ApiResponse.ok(recommendRoutineDto));
    }
}
