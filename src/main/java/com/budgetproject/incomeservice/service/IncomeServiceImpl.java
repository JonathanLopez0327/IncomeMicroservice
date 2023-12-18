package com.budgetproject.incomeservice.service;

import com.budgetproject.incomeservice.entity.Income;
import com.budgetproject.incomeservice.exception.IncomeServiceCustomException;
import com.budgetproject.incomeservice.external.client.AccountService;
import com.budgetproject.incomeservice.model.IncomeRequest;
import com.budgetproject.incomeservice.model.IncomeResponse;
import com.budgetproject.incomeservice.repository.IncomeRepositoy;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepositoy incomeRepositoy;

    @Autowired
    private AccountService accountService;

    private String getCurrentPeriod() {
        String period = "";
        try {
            Instant currentInstant = Instant.now();
            YearMonth yearMonth = YearMonth.from(currentInstant.atZone(ZoneId.systemDefault()));
            // Formatear el YearMonth como "yyyyMM"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            period = yearMonth.format(formatter);
        } catch (Exception var3) {
            log.error("Error getting current period", var3);
        }

        return period;
    }

    @Override
    public long recordIncome(@Valid IncomeRequest incomeRequest) {
        log.info("Recording Income...");

        log.info("Crediting amount to account {}", incomeRequest.getAccountId());
        accountService.creditAmount(incomeRequest.getAccountId(), incomeRequest.getAmount());

        Income income = Income.builder()
                .accountId(incomeRequest.getAccountId())
                .incomeDescription(incomeRequest.getIncomeDescription())
                .amount(incomeRequest.getAmount())
                .incomeType(incomeRequest.getIncomeType())
                .date(Instant.now())
                .period(getCurrentPeriod())
                .build();

        incomeRepositoy.save(income);
        log.info("Income recorded");

        return income.getIncomeId();
    }

    @Override
    public void updateIncome(long incomeId, @Valid IncomeRequest incomeRequest) {
        log.info("Update Income for Id: {}", incomeId);

        // implement exception
        Income income = incomeRepositoy.findById(incomeId).orElseThrow(() -> new IncomeServiceCustomException(
                "Income with given Id not found", "INCOME_NOT_FOUND"
        ));

        if (incomeRequest.getIncomeDescription() != null) {
            income.setIncomeDescription(incomeRequest.getIncomeDescription());
        }

        if (incomeRequest.getIncomeType() != null) {
            income.setIncomeType(incomeRequest.getIncomeType());
        }

        if (incomeRequest.getAccountId() != 0 && incomeRequest.getAmount() != 0) {
            log.info("Removing income to account : {}", income.getAccountId());
            accountService.debitAmount(income.getAccountId(), income.getAmount());

            try {
                log.info("Adding income to account : {}", incomeRequest.getAccountId());
                accountService.creditAmount(incomeRequest.getAccountId(), incomeRequest.getAmount());

                income.setAccountId(incomeRequest.getAccountId());
                income.setAmount(incomeRequest.getAmount());
                incomeRepositoy.save(income);
                log.info("Income updated successfully!");
            } catch (IncomeServiceCustomException e) {
                log.error("Error modifying income", e);

                // roll back
                log.info("Removing income from account: {}", income.getAccountId());
                accountService.creditAmount(income.getAccountId(), income.getAmount());

                throw new IncomeServiceCustomException(
                        "Error modifying income",
                        "BAD_REQUEST"
                );
            }
        }

        log.info("Income updated successfully!");
    }

    @Override
    public List<IncomeResponse> getAllIncome() {
        log.info("Get all incomes");
        List<Income> incomeList = incomeRepositoy.findAll();

        return incomeList
                .stream()
                .map(incomeEntity-> {
                    IncomeResponse incomeResponse = new IncomeResponse();
                    BeanUtils.copyProperties(incomeEntity, incomeResponse);
                    return incomeResponse;
                }).toList();
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

    @Override
    public void deleteIncome(long incomeId) {
        log.info("Get the income for incomeId: {}", incomeId);
        Income income = incomeRepositoy.findById(incomeId)
                .orElseThrow(() -> new IncomeServiceCustomException("Income with given id not found", "INCOME_NOT_FOUND"));

        log.info("Removing income from account : {}", income.getAccountId());
        accountService.debitAmount(income.getAccountId(), income.getAmount());
        incomeRepositoy.delete(income);
        log.info("Expense with id {} has been removed.", incomeId);

    }
}
