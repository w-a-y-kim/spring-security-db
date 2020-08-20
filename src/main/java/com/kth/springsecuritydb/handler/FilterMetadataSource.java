package com.kth.springsecuritydb.handler;

import com.kth.springsecuritydb.service.AdminMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 각 request URL 별로 권한을 조회
 */
@Slf4j
public class FilterMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private AdminMenuService menuService;
    private List<RequestMatcher> ignoreMatchers = new ArrayList<>();

    public FilterMetadataSource(String... ignorePatterns) {
        for (String pattern : ignorePatterns) {
            ignoreMatchers.add(new AntPathRequestMatcher(pattern));
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();

        // ignore 패턴 조회 후 matche시 통과
        for (RequestMatcher matcher : ignoreMatchers) {
            if (matcher.matches(fi.getHttpRequest())) {
                log.debug("ignored url [matcher={}, url={}]", ((AntPathRequestMatcher)matcher).getPattern(), url);
                return null;
            }
        }
        Set<String> roles = menuService.findByRole(url);
        return org.springframework.security.access.SecurityConfig.createList(roles.toArray(new String[roles.size()]));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
