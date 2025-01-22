package com.ruty.rutyserver.member.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.member.service.MemberService;
import com.ruty.rutyserver.member.dto.MemberDto;
import com.ruty.rutyserver.member.dto.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "초기 회원설정 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "선택약관, 닉네임 설정(인증토큰 필요)",
            description = "소셜 로그인 이후, 선택약관, 닉네임을 설정합니다.")
    @PutMapping("/sign")
    public ResponseEntity<?> signUp(@RequestBody MemberDto memberDto,
                                    Principal principal) {
        Long memberId = memberService.signUpMember(principal.getName(), memberDto);
        return ResponseEntity.ok(ApiResponse.updated(memberId));
    }

    @Operation(
            summary = "약관동의 조회(인증토큰 필요)",
            description = "Null만 아니면 필수약관은 동의한 상태임.<br>Null -> 필수약관 동의x<br>true -> 선택약관 동의o<br>false -> 선택약관 동의x")
    @GetMapping("/isAgree")
    public ResponseEntity<?> isMemberAgree(Principal principal) {
        boolean memberAgree = memberService.isMemberAgree(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(memberAgree));
    }
}
