package com.kth.springsecuritydb.repository;

import com.kth.springsecuritydb.model.AdminMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMemberRepository extends JpaRepository<AdminMember, Long> {
    /**
     * find user id
     * @param userId
     * @return
     */
    AdminMember findByUserId(String userId);
}
