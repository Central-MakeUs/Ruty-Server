package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.member.MemberInfoDto;
import com.ruty.rutyserver.dto.recommend.RecommendRoutineDto;
import com.ruty.rutyserver.dto.routine.CategoryLevelInfoDto;
import com.ruty.rutyserver.dto.routine.RoutineDto;
import com.ruty.rutyserver.service.MemberServiceImpl;
import com.ruty.rutyserver.service.RecommendServiceImpl;
import com.ruty.rutyserver.service.RoutineServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "개발용 API", description = "데이터 조회용(토큰 필요x)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dev")
public class DevController {

    private final RecommendServiceImpl recommendService;
    private final RoutineServiceImpl routineService;
    private final MemberServiceImpl memberService;

    @Operation(summary = "추천받은 루틴 전체 조회")
    @GetMapping("/recommends")
    public ResponseEntity<?> getAllRecommends() {
        List<RecommendRoutineDto> allRecommends = recommendService.getAllRecommends();
        return ResponseEntity.ok(ApiResponse.ok(allRecommends));
    }

    @Operation(summary = "회원 전체 조회")
    @GetMapping("/members")
    public ResponseEntity<?> getAllMembers() {
        List<MemberInfoDto> memberInfoDtos = memberService.getAllMembers();
        return ResponseEntity.ok(ApiResponse.ok(memberInfoDtos));
    }
    @Operation(summary = "회원이 담은 루틴 전체 조회")
    @GetMapping("/routines")
    public ResponseEntity<?> getAllRoutines() {
        List<RoutineDto> myAllRoutines = routineService.getAllRoutines();
        return ResponseEntity.ok(ApiResponse.ok(myAllRoutines));
    }

    @Operation(summary = "카테고리 레벨 전체 조회")
    @GetMapping("/levels")
    public ResponseEntity<?> getAllCategoryLevels() {
        List<CategoryLevelInfoDto> allCategoryLevels = routineService.getAllCategoryLevels();
        return ResponseEntity.ok(ApiResponse.ok(allCategoryLevels));
    }
}
