package com.kth.springsecuritydb.repository;

import com.kth.springsecuritydb.model.AdminMember;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AdminMemberRepositoryTests {
    @Autowired
    private AdminMemberRepository adminMemberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void findAdminMemberTest(){
        final String userId = "testTest";
        final String userName = "테스트";
        AdminMember adminMember = AdminMember.builder().userId(userId).name(userName).password(passwordEncoder.encode("password")).build();
        adminMemberRepository.save(adminMember);
        AdminMember result = adminMemberRepository.findByUserId(userId);

        assertNotNull(result);
        assertEquals(result.getName(), userName);
    }
}
