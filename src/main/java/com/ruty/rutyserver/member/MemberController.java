package com.ruty.rutyserver.member;

import com.ruty.rutyserver.common.ApiResponse;
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

@Tag(name = "회원 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "소셜 로그인 후, 닉네임 설정")
    @PutMapping("/sign")
    public ResponseEntity<?> signUpNickName(@RequestBody MemberDto memberDto,
                                         Principal principal) {
        log.info("Principal: " + principal.getName());
        Long memberId = memberService.updateMemberNickname(principal.getName(), memberDto);

        return ResponseEntity.ok(ApiResponse.updated(memberId));
    }

    @Operation(summary = "회원 전체정보 확인 (paginaction x)")
    @GetMapping()
    public ResponseEntity<?> getAllMembers() {
        List<MemberInfoDto> memberInfoDtos = memberService.getAllMembers();
        return ResponseEntity.ok(ApiResponse.ok(memberInfoDtos));
    }

}
