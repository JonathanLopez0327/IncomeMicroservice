package com.budgetproject.incomeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeResponse {
    private long incomeId;
    private long accountId;
    private String incomeDescription;
    private long amount;
    private String incomeType;
    private String period;
}
