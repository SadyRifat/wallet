package com.digital.wallet.entity.transaction;

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
@Table(name = "transaction_limits", schema = DB_SCHEMA)
public class Limits extends AuditEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "limit_amount")
    private Long limitAmount;

    @Column(name = "limit_type")
    private String limitType;
}
