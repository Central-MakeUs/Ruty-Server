package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.common.ApiResponse;
import com.ruty.rutyserver.security.oauth.JwtDto;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.security.oauth.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class OauthController {
    private final OAuthLoginService oAuthLoginService;
    @GetMapping("/oauth2/code/google")
    public ResponseEntity<?> loginGoogle(@RequestParam(name = "code") String code) {
        log.info("controller come in.....");
        JwtDto jwtDto = oAuthLoginService.loginSocial("google", code);
        return ResponseEntity.ok(ApiResponse.ok(jwtDto));
    }

    @PostMapping("/oauth2/code/apple")
    public ResponseEntity<?> loginApple(@RequestParam(name = "code") String code) {
        log.info("controller come in.....");
        JwtDto jwtDto = oAuthLoginService.loginSocial("apple", code);
        return ResponseEntity.ok(ApiResponse.ok(jwtDto));
    }
}
