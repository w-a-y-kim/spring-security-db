package com.kth.springsecuritydb.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class AdminMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 유저 ID **/
    @Column(unique = true)
    private String userId;
    /** 이름 */
    @Column(nullable = false)
    private String name;
    /** 비밀번호 **/
    @Column(nullable = false)
    private String password;
    /** role members */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="admin_member_id")
    private List<RoleMember> roleMembers;
}
