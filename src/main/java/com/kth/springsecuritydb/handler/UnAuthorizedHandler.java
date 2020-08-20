package com.kth.springsecuritydb.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인을 하지 않은상태에서 요청한경우 동작
 */
@Slf4j
public class UnAuthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("require login");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}

