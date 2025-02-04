package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.dto.member.MemberDto;
import com.ruty.rutyserver.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "초기 회원설정 API", description = "피그마[1-2]")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "선택약관, 닉네임 정보 저장",
            description = "소셜 로그인이후, 선택약관, 닉네임을 설정함.")
    @PutMapping("/sign")
    public ResponseEntity<?> signUp(@RequestBody MemberDto memberDto,
                                    Principal principal) {
        Long memberId = memberService.signUpMember(principal.getName(), memberDto);
        return ResponseEntity.ok(ApiResponse.updated(memberId));
    }

    @Operation(
            summary = "약관동의 조회",
            description = "Null만 아니면 필수약관은 동의한 상태임.<br>Null -> 필수약관 동의x<br>true -> 선택약관 동의o<br>false -> 선택약관 동의x")
    @GetMapping("/isAgree")
    public ResponseEntity<?> isMemberAgree(Principal principal) {
        boolean memberAgree = memberService.isMemberAgree(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(memberAgree));
    }

}
