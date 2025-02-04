package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.improvement_goals.ImprovementGoalDto;
import com.ruty.rutyserver.service.IGService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "개선점 API", description = "피그마[3]")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class IGController {
    private final IGService igService;

    @Operation(
            summary = "개선점 전체 조회",
            description = "개선점 후보를 모두 불러옴. 8개가 전체임.")
    @GetMapping
    public ResponseEntity<?> getAllGoals() {
        List<ImprovementGoalDto> allGoals = igService.getAllGoals();
        return ResponseEntity.ok(ApiResponse.ok(allGoals));
    }
}
