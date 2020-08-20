package com.kth.springsecuritydb.controller;

import com.kth.springsecuritydb.model.AdminMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @GetMapping("/my-account")
    public AdminMember myAccount() {
        log.debug("account get success");
        return AdminMember.builder().userId("testUser").name("테스트").build();
    }
}
