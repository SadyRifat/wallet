package com.digital.wallet.entity.notification;

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
@Table(name = "notifications", schema = DB_SCHEMA)
public class Notifications extends AuditEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "message")
    private String message;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "status")
    private String status;
}
