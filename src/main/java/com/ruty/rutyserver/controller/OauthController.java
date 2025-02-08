package com.ruty.rutyserver.controller;

import com.ruty.rutyserver.security.oauth.JwtDto;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.security.oauth.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class OauthController {
    private final OAuthLoginService oAuthLoginService;
    @PostMapping("/login/{socialType}")
    public ResponseEntity<JwtDto> loginSocial(@RequestParam(name = "code") String code,
                                              @PathVariable(name = "socialType") SocialType socialType) {

        oAuthLoginService.loginSocial(socialType, code)

        return null;
    }
}
