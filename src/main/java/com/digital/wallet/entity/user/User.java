package com.digital.wallet.entity.user;

import com.digital.wallet.entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.digital.wallet.utls.StaticTextConfig.DB_SCHEMA;

@Getter
@Setter
@Entity
@Table(name = "users", schema = DB_SCHEMA)
public class User extends AuditEntity {
    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "otp")
    private String otp;

    @Column(name = "user_type")
    private String type;

    @Column(name = "is_enabled")
    private boolean enabled = false;

    @Column(name = "is_not_expired")
    private boolean accountNonExpired = true;

    @Column(name = "is_not_locked")
    private boolean accountNonLocked = true;

    @Column(name = "is_cred_not_expired")
    private boolean credentialsNonExpired = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new HashSet<>();
}
