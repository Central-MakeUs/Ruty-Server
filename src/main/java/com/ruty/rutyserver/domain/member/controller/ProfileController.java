package com.ruty.rutyserver.domain.member.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.domain.member.dto.MemberInfoDto;
import com.ruty.rutyserver.domain.member.dto.MemberUpdateDto;
import com.ruty.rutyserver.domain.member.service.MemberService;
import com.ruty.rutyserver.domain.recommend.dto.RecommendRoutineDto;
import com.ruty.rutyserver.domain.routine.RoutineDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "프로필 API", description = "(jwt 토큰을 기반으로 프로필 조회하기에 토큰 필요함.)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final MemberService memberService;

    @Operation(summary = "회원 프로필 전체 조회(개발용)")
    @GetMapping("/all")
    public ResponseEntity<?> getAllMembers() {
        List<MemberInfoDto> memberInfoDtos = memberService.getAllMembers();
        return ResponseEntity.ok(ApiResponse.ok(memberInfoDtos));
    }

    @Operation(summary = "프로필 조회")
    @GetMapping
    public ResponseEntity<?> getMyProfile(Principal principal) {
        MemberInfoDto myProfile = memberService.getMyProfile(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(myProfile));
    }

    @Operation(summary = "내 추천받은 루틴 보기")
    @GetMapping("/recommends")
    public ResponseEntity<?> getMyRecommendRoutines(Principal principal) {
        List<RecommendRoutineDto> myRecommendRoutines = memberService.getMyRecommendRoutines(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(myRecommendRoutines));
    }

    @Operation(summary = "내 루틴 보기")
    @GetMapping("/routines")
    public ResponseEntity<?> getMyRoutines(Principal principal) {
        List<RoutineDto> myRoutines = memberService.getMyRoutines(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(myRoutines));
    }

    @Operation(summary = "프로필 수정")
    @PutMapping
    public ResponseEntity<?> saveMyProfile(@RequestBody MemberUpdateDto memberUpdateDto,
                                           Principal principal) {
        MemberInfoDto updateMyProfile = memberService.updateMyProfile(principal.getName(), memberUpdateDto);
        return ResponseEntity.ok(ApiResponse.updated(updateMyProfile));
    }

    @Operation(summary = "프로필 삭제(회원 탈퇴)")
    @DeleteMapping
    public ResponseEntity<?> deleteMyProfile(Principal principal) {
        Long memberId = memberService.deleteMyProfile(principal.getName());
        return ResponseEntity.ok(ApiResponse.delete(memberId));
    }
}
