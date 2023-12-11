package com.budgetproject.incomeservice.service;

import com.budgetproject.incomeservice.model.IncomeRequest;
import com.budgetproject.incomeservice.model.IncomeResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface IncomeService {
    long recordIncome(@Valid IncomeRequest incomeRequest);

    void updateIncome(long incomeId, @Valid IncomeRequest incomeRequest);

    List<IncomeResponse> getAllIncome();

    IncomeResponse getIncomeById(long incomeId);

    void deleteIncome(long incomeId);
}
