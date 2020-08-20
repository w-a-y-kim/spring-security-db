package com.kth.springsecuritydb.config;

import com.kth.springsecuritydb.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /** role prefix */
    public static final String ROLE_PREFIX = "ROLE_";
    /** login process URL */
    private static final String LOGIN_PROCESS_URL = "/login-process";
    /** login success URL */
    private static final String LOGIN_SUCCESS_URL = "/login-after";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.exceptionHandling().accessDeniedHandler(new UrlDeniedHandler()).authenticationEntryPoint(new UnAuthorizedHandler());
        http.addFilterAt(this.getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(this.filterSecurityInterceptor(), FilterSecurityInterceptor.class);
    }

    /**
     * 유저ID, 비밀번호를 request body에서 꺼내기 위한 Filter
     *
     * @return authentication filter
     */
    public CustomAuthenticationHandler getAuthenticationFilter() throws Exception {
        CustomAuthenticationHandler authFilter = new CustomAuthenticationHandler();
        authFilter.setAuthenticationManager(super.authenticationManagerBean());
        authFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(LOGIN_SUCCESS_URL));
        authFilter.setAuthenticationFailureHandler(new LoginFailHandler());
        // 로그인 처리를 수행할 URL
        authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_PROCESS_URL));
        return authFilter;
    }

    /**
     * URL 권한체크 Filter
     *
     * @return filter security interceptor
     */
    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setAuthenticationManager(super.authenticationManagerBean());
        filterSecurityInterceptor.setSecurityMetadataSource(this.getFilterMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(this.affirmativeBased());
        return filterSecurityInterceptor;
    }

    /**
     * Gets filter metadata source.
     *
     * @return the filter metadata source
     */
    @Bean
    public FilterMetadataSource getFilterMetadataSource() {
        return new FilterMetadataSource("/js/**", "/css/**", "/images/**");
    }

    /**
     * Affirmative based.
     *
     * @return the affirmative based
     */
    public AffirmativeBased affirmativeBased() {
        RoleVoter voter = new RoleVoter();
        voter.setRolePrefix(ROLE_PREFIX);

        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(voter);
        return new AffirmativeBased(accessDecisionVoters);
    }

    /**
     * password encoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
