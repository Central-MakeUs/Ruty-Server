package com.ruty.rutyserver.security.jwt;

import com.ruty.rutyserver.exception.JwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Security;

public class JwtUtil {
    public static String getLoginMemberEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new JwtException();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername(); // 유저 이메일 반환
        } else if (principal instanceof String) {
            return (String) principal; // principal이 문자열일 경우 (ex: JWT 인증 시)
        }

        return null;
    }
}
