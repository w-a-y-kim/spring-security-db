package com.kth.springsecuritydb.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 권한정보
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 권한명 */
    @Column(nullable = false)
    private String roleName;
    @OneToMany
    @JoinColumn(name="role_id")
    private List<RoleMenuGroup> roleMenuGroups;
}
