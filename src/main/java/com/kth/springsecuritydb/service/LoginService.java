package com.kth.springsecuritydb.service;

import com.kth.springsecuritydb.model.AdminMember;
import com.kth.springsecuritydb.model.AdminMemberWrapper;
import com.kth.springsecuritydb.repository.AdminMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
    private final AdminMemberRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        AdminMember adminMember = repository.findByUserId(userId);
        if (adminMember == null) {
            log.info("User not found userId[{}]", userId);
            throw new UsernameNotFoundException("User not found with userId");
        }
        return new AdminMemberWrapper(adminMember);
    }
}
