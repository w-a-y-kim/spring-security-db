package com.kth.springsecuritydb.repository;

import com.kth.springsecuritydb.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository repository;

    @Test
    @Transactional(readOnly = true)
    public void findAdminMenuTest(){
        List<Role> results = repository.findByResourceUrl("/api/v1/my-account");
        assertNotNull(results);
        assertTrue(results.size() == 1);
    }
}
