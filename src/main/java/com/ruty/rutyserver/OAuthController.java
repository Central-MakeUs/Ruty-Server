package com.ruty.rutyserver;

import com.ruty.rutyserver.security.oauth.dto.common.SessionMember;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class OAuthController {

    private final HttpSession httpSession;
    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("healthy!!");
    }

    @GetMapping("/googleLogin")
    public String googleLogin(Model model) {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        if(member != null) {
            model.addAttribute("member", member.getName());
        }
        return "loginForm";
    }

    @GetMapping("/google")
    public void getGoogleLoginUrl() {
        log.info("사용자로부터 google oauth 로그인 요청 받음.");

    }
}
