package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.improvement_goals.ImprovementGoalReq;
import com.ruty.rutyserver.service.IGService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "개선하고 싶은 점 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goals")
public class IGController {
    private final IGService igService;

    @Operation(
            summary = "개선하고 싶은 점 전체 조회",
            description = "피그마[3]<br>개선하고 싶은 점 후보를 모두 불러옴.")
    @GetMapping
    public ResponseEntity<?> getAllGoals() {
        List<ImprovementGoalReq> allGoals = igService.getAllGoals();
        return ResponseEntity.ok(ApiResponse.ok(allGoals));
    }
}
