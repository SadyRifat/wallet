package com.digital.wallet.entity.user;

import com.digital.wallet.entity.AuditEntity;
import com.digital.wallet.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.digital.wallet.utls.StaticTextConfig.DB_SCHEMA;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles", schema = DB_SCHEMA)
public class Role extends AuditEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private ERole name;
}
