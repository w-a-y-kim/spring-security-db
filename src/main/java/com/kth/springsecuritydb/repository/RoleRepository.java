package com.kth.springsecuritydb.repository;

import com.kth.springsecuritydb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * find resourceUrl
     * @param resourceUrl
     * @return
     */
    @Query("FROM Role AS role " +
            "INNER JOIN role.roleMenuGroups AS menuGroup " +
            "INNER JOIN menuGroup.adminMenu AS menu " +
            "WHERE menu.resourceUrl = ?1")
    List<Role> findByResourceUrl(String resourceUrl);
}
