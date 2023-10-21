package com.budgetproject.incomeservice.service;

import com.budgetproject.incomeservice.model.IncomeRequest;

public interface IncomeService {
    long recordIncome(IncomeRequest incomeRequest);
}
