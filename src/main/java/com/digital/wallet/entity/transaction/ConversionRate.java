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
@Table(name = "transaction_conversion_rate", schema = DB_SCHEMA)
public class ConversionRate extends AuditEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "from_currency_code")
    private String fromCurrencyCode;

    @Column(name = "to_currency_code")
    private String toCurrencyCode;

    @Column(name = "rate")
    private double rate;
}
