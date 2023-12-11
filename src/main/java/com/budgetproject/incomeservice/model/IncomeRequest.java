package com.budgetproject.incomeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeRequest {

    // This class represent model for request

    private long accountId;
    private String incomeDescription;
    private long amount;
    private String incomeType;
}
