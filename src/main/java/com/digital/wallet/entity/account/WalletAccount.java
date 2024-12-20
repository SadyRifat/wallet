package com.digital.wallet.entity.account;

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
@Table(name = "wallet", schema = DB_SCHEMA)
public class WalletAccount extends AuditEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "balance")
    private double balance;
}
