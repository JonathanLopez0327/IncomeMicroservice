package com.budgetproject.incomeservice.service;

import com.budgetproject.incomeservice.entity.Income;
import com.budgetproject.incomeservice.model.IncomeRequest;
import com.budgetproject.incomeservice.repository.IncomeRepositoy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepositoy incomeRepositoy;

    @Override
    public long recordIncome(IncomeRequest incomeRequest) {
        log.info("Recording Income...");

        Income income = Income.builder()
                .incomeDescription(incomeRequest.getIncomeDescription())
                .amount(incomeRequest.getAmount())
                .incomeType(incomeRequest.getIncomeType().toString())
                .date(Instant.now())
                .period(incomeRequest.getPeriod())
                .build();

        incomeRepositoy.save(income);
        log.info("Income recorded");

        return income.getIncomeId();
    }
}
