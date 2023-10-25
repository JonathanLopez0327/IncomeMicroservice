package com.budgetproject.incomeservice.service;

import com.budgetproject.incomeservice.entity.Income;
import com.budgetproject.incomeservice.exception.IncomeServiceCustomException;
import com.budgetproject.incomeservice.model.IncomeRequest;
import com.budgetproject.incomeservice.model.IncomeResponse;
import com.budgetproject.incomeservice.repository.IncomeRepositoy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepositoy incomeRepositoy;

    @Override
    public long recordIncome(IncomeRequest incomeRequest) {
        log.info("Recording Income...");

        Income income = Income.builder()
                .accountId(incomeRequest.getAccountId())
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

    @Override
    public void updateIncome(long incomeId, IncomeRequest incomeRequest) {
        log.info("Update Income for Id: {}", incomeId);

        // implement exception
        Income income = incomeRepositoy.findById(incomeId).orElseThrow(() -> new IncomeServiceCustomException(
                "Income with given Id not found", "INCOME_NOT_FOUND"
        ));

        if (incomeRequest.getAccountId() != 0) {
            income.setAccountId(incomeRequest.getAccountId());
        }

        if (incomeRequest.getIncomeDescription() != null) {
            income.setIncomeDescription(incomeRequest.getIncomeDescription());
        }

        if (incomeRequest.getAmount() != 0) {
            income.setAmount(incomeRequest.getAmount());
        }

        if (incomeRequest.getIncomeType() != null) {
            income.setIncomeType(incomeRequest.getIncomeType());
        }

        if (incomeRequest.getPeriod() != null) {
            income.setPeriod(incomeRequest.getPeriod());
        }

        incomeRepositoy.save(income);
        log.info("Income updated successfully!");
    }

    @Override
    public List<IncomeResponse> getAllIncome() {
        log.info("Get all incomes");
        List<Income> incomeList = incomeRepositoy.findAll();

        List<IncomeResponse> incomes
                = incomeList
                .stream()
                .map(incomeEntity-> {
                    IncomeResponse incomeResponse = new IncomeResponse();
                    BeanUtils.copyProperties(incomeEntity, incomeResponse);
                    return incomeResponse;
                }).toList();

        return incomes;
    }

    @Override
    public IncomeResponse getIncomeById(long incomeId) {
        log.info("Get the income for incomeId: {}", incomeId);

        Income income = incomeRepositoy.findById(incomeId)
                .orElseThrow(() -> new IncomeServiceCustomException("Income with given id not found", "INCOME_NOT_FOUND"));

        IncomeResponse incomeResponse = new IncomeResponse();

        copyProperties(income, incomeResponse);
        return incomeResponse;
    }
}
