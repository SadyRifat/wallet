package com.digital.wallet.entity.user;

import com.digital.wallet.entity.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static com.digital.wallet.utls.StaticTextConfig.DB_SCHEMA;

@Getter
@Setter
@Entity
@Table(name = "user_info", schema = DB_SCHEMA)
public class UserInfo extends AuditEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone_no")
    private String contactPhone;
}
