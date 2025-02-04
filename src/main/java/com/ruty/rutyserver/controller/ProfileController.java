package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.member.MemberInfoDto;
import com.ruty.rutyserver.dto.member.MemberUpdateDto;
import com.ruty.rutyserver.service.MemberService;
import com.ruty.rutyserver.dto.recommend.RecommendRoutineDto;
import com.ruty.rutyserver.dto.routine.RoutineDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "프로필 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final MemberService memberService;

    @Operation(summary = "프로필 조회")
    @GetMapping
    public ResponseEntity<?> getMyProfile(Principal principal) {
        MemberInfoDto myProfile = memberService.getMyProfile(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(myProfile));
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
