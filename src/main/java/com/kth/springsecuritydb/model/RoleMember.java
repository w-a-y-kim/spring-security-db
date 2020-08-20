package com.kth.springsecuritydb.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class RoleMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** admin member 테이블 시퀀스 */
    @Column(name="admin_member_id", nullable = false)
    private Long adminMemberId;
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
}
