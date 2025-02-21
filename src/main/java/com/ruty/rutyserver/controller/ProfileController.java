package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.member.MemberInfoDto;
import com.ruty.rutyserver.dto.member.MemberUpdateDto;
import com.ruty.rutyserver.security.jwt.JwtUtil;
import com.ruty.rutyserver.service.MemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "프로필 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final MemberServiceImpl memberService;

    @Operation(summary = "프로필 조회")
    @GetMapping
    public ResponseEntity<?> getMyProfile() {
        String email = JwtUtil.getLoginMemberEmail();

        MemberInfoDto myProfile = memberService.getMyProfile(email);
        return ResponseEntity.ok(ApiResponse.ok(myProfile));
    }

    @Operation(summary = "프로필 수정")
    @PutMapping
    public ResponseEntity<?> saveMyProfile(@RequestBody MemberUpdateDto memberUpdateDto) {
        String email = JwtUtil.getLoginMemberEmail();

        MemberInfoDto updateMyProfile = memberService.updateMyProfile(email, memberUpdateDto);
        return ResponseEntity.ok(ApiResponse.updated(updateMyProfile));
    }

    @Operation(summary = "프로필 삭제(회원 탈퇴)")
    @DeleteMapping
    public ResponseEntity<?> deleteMyProfile(@RequestParam(name = "code", defaultValue = "google") String code) {
        String email = JwtUtil.getLoginMemberEmail();

        Long memberId = memberService.deleteMyProfile(email, code);
        return ResponseEntity.ok(ApiResponse.delete(memberId));
    }
}
