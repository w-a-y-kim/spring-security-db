package com.kth.springsecuritydb.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인은 했으나 권한이 없을때 동작하는 Handler
 */
@Slf4j
public class UrlDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        log.error("access denied.");
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
