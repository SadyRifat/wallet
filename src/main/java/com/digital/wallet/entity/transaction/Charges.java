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
@Table(name = "transaction_charges", schema = DB_SCHEMA)
public class Charges extends AuditEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "charge_model")
    private String chargeModel;

    @Column(name = "amount")
    private double amount;
}
