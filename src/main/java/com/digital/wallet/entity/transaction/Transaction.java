package com.digital.wallet.entity.transaction;

import com.digital.wallet.entity.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

import static com.digital.wallet.utls.StaticTextConfig.DB_SCHEMA;

@Getter
@Setter
@Entity
@Table(name = "transactions", schema = DB_SCHEMA)
public class Transaction extends AuditEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "sender_wallet_id")
    private String senderWalletId;

    @Column(name = "receiver_wallet_id")
    private String receiverWalletId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "fee")
    private double fee;

    @Column(name = "transaction_date")
    private ZonedDateTime transactionDate;

    @Column(name = "conversion_rate")
    private double conversionRate;

    @Column(name = "conversion_amount")
    private double conversionAmount;

    @Column(name = "remarks")
    private String remarks;
}
