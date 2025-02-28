package com.ruty.rutyserver.security.handler;

import com.ruty.rutyserver.common.exception.ExceptionSituation;
import com.ruty.rutyserver.exception.JwtAccessDeniedException;
import com.ruty.rutyserver.exception.JwtAuthenticationException;
import com.ruty.rutyserver.util.HttpResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.warn("Access Denied: ", accessDeniedException);
        ExceptionSituation exception = new ExceptionSituation("권한이 없습니다.", HttpStatus.UNAUTHORIZED, 7002);
        HttpResponseUtil.setErrorResponse(response, HttpStatus.FORBIDDEN, exception);

    }
}
