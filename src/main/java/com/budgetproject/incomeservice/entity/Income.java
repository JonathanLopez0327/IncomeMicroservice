package com.budgetproject.incomeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Income {

    // This class represent the table of database Income

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long incomeId;

    @Column(name = "INCOME_DESCRIPTION")
    private String incomeDescription;
    @Column(name = "AMOUNT")
    private long amount;
    @Column(name = "INCOME_TYPE")
    private String typeAmount;
    @Column(name = "INCOME_DATE")
    private Instant date;
    @Column(name = "INCOME PERIOD")
    private String period;
}
