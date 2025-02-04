package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.service.RecommendService;
import com.ruty.rutyserver.dto.recommend.RecommendRoutineDto;
import com.ruty.rutyserver.dto.routine.RoutineReq;
import com.ruty.rutyserver.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "추천루틴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendService recommendService;
    private final RoutineService routineService;
    @Operation(
            summary = "프롬포트 요청하기(인증토큰 필요)",
            description = "파라미터에 개선하고 싶은 점을 반점으로 구분해서 넣어주면 됨.<br>" +
                    "ex: /api/recommend/gpt?안정적인 주거환경 만들기, 외로움과 고립감 해소하기")
    @GetMapping("/gpt")
    public ResponseEntity<?> chat(@RequestParam(name = "prompt") String prompt,
                                  Principal principal){
        List<RecommendRoutineDto> gptResponse = recommendService.getGptResponse(prompt, principal.getName());
        return ResponseEntity.ok(ApiResponse.created(gptResponse));
    }

    @Operation(
            summary = "추천받은 루틴 저장(인증토큰 필요)",
            description = "추천받은 루틴을 저장합니다. gpt를 통해 추천받은 루틴을 저장한다.<br><br>PathVariable: 추천받은 루틴의 pk값")
    @PostMapping("/{recommendId}")
    public ResponseEntity<?> saveRecommendRoutine(
            @PathVariable(name = "recommendId") Long recommendId,
            @RequestBody RoutineReq routineReq,
            Principal principal) {
        Long routineId = routineService.saveRoutine(recommendId, routineReq, principal.getName());
        return ResponseEntity.ok(ApiResponse.created(routineId));
    }

    @Operation(summary = "나의 추천받은 루틴 전체보기")
    @GetMapping("/all")
    public ResponseEntity<?> getAllRecommend(Principal principal) {
        List<RecommendRoutineDto> recommendRoutineDtos = recommendService.getAllRecommend(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(recommendRoutineDtos));
    }
}
