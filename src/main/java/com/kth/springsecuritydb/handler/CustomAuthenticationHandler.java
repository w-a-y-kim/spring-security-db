package com.kth.springsecuritydb.handler;

import com.google.gson.Gson;
import com.kth.springsecuritydb.model.AdminMember;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

/**
 * custom username password filter
 */
public class CustomAuthenticationHandler extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AdminMember adminMember = this.getInputData(request);
        if (adminMember == null || StringUtils.isBlank(adminMember.getUserId()) || StringUtils.isBlank(adminMember.getPassword())) {
            throw new AuthenticationServiceException("login info is empty");
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(adminMember.getUserId(), adminMember.getPassword());

        super.setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * request에서 로그인ID, 패스워드를 꺼내어 반환한다.
     *
     * @param request
     * @return
     */
    private AdminMember getInputData(HttpServletRequest request) {
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            return new Gson().fromJson(reader, AdminMember.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
