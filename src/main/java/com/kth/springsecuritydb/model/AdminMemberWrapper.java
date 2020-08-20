package com.kth.springsecuritydb.model;

import com.kth.springsecuritydb.config.SecurityConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AdminMemberWrapper extends org.springframework.security.core.userdetails.User {
    @Setter(AccessLevel.NONE)
    private AdminMember adminMember;

    public AdminMemberWrapper(AdminMember adminMember) {
        super(adminMember.getUserId(), adminMember.getPassword(), makeGrantedAuthority(adminMember));
        this.adminMember = adminMember;
    }

    private static List<GrantedAuthority> makeGrantedAuthority(AdminMember adminMember) {
        List<Role> roles = adminMember.getRoleMembers().stream().map(i -> i.getRole()).collect(Collectors.toList());
        return roles.stream().map(item -> new SimpleGrantedAuthority(SecurityConfig.ROLE_PREFIX + item.getRoleName())).collect(Collectors.toList());
    }
}
