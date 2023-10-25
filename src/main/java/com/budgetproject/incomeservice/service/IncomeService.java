package com.budgetproject.incomeservice.service;

import com.budgetproject.incomeservice.model.IncomeRequest;
import com.budgetproject.incomeservice.model.IncomeResponse;

import java.util.List;

public interface IncomeService {
    long recordIncome(IncomeRequest incomeRequest);

    void updateIncome(long incomeId, IncomeRequest incomeRequest);

    List<IncomeResponse> getAllIncome();

    IncomeResponse getIncomeById(long incomeId);
}
