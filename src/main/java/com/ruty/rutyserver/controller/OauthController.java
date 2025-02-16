package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.security.oauth.JwtDto;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.security.oauth.OAuthLoginService;
import com.ruty.rutyserver.security.oauth.SocialLoginDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Tag(name = "소셜 로그인 api", description = "둘다 같은 동작을 하는데 테스트해보시고, 요청 잘 되는지 카톡주세요!")
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class OauthController {
    private final OAuthLoginService oAuthLoginService;
    @GetMapping("/oauth2/code/{socialType}")
    public ResponseEntity<?> login1(@PathVariable(name = "socialType") String socialType,
                                    @RequestBody SocialLoginDto socialLoginDto) throws GeneralSecurityException, IOException {
        JwtDto jwtDto = oAuthLoginService.loginSocial(socialType, socialLoginDto);
        return ResponseEntity.ok(ApiResponse.ok(jwtDto));
    }

    @PostMapping("/oauth2/code/{socialType}")
    public ResponseEntity<?> login2(@PathVariable(name = "socialType")String socialType,
                                    @RequestBody SocialLoginDto socialLoginDto) throws GeneralSecurityException, IOException {
        JwtDto jwtDto = oAuthLoginService.loginSocial(socialType, socialLoginDto);
        return ResponseEntity.ok(ApiResponse.ok(jwtDto));
    }
}
