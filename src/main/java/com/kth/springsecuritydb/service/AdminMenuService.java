package com.kth.springsecuritydb.service;

import com.kth.springsecuritydb.config.SecurityConfig;
import com.kth.springsecuritydb.model.Role;
import com.kth.springsecuritydb.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminMenuService {
    private final RoleRepository repository;

    public Set<String> findByRole(String resourceUrl) {
        List<Role> roles = repository.findByResourceUrl(resourceUrl);
        if(roles == null) {
            return null;
        }
        return roles.stream().map(i -> SecurityConfig.ROLE_PREFIX + i.getRoleName()).collect(Collectors.toSet());
    }
}
