package com.ruty.rutyserver.improvementGoals;

import com.ruty.rutyserver.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goals")
public class IGController {
    private final IGService igService;

    @Operation(
            summary = "개선하고 싶은 점 전체 불러오기",
            description = "피그마[3]<br>사용자한테  ")
    @GetMapping
    public ResponseEntity<?> getAllGoals() {
        List<ImprovementGoalReq> allGoals = igService.getAllGoals();
        return ResponseEntity.ok(ApiResponse.ok(allGoals));
    }
}
