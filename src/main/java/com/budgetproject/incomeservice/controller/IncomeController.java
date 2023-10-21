package com.budgetproject.incomeservice.controller;

import com.budgetproject.incomeservice.model.IncomeRequest;
import com.budgetproject.incomeservice.service.IncomeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/income")
@Log4j2
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping("/recordIncome")
    public ResponseEntity<Long> recordIncome(@RequestBody IncomeRequest incomeRequest) {
        long incomeId = incomeService.recordIncome(incomeRequest);
        return new ResponseEntity<>(incomeId, HttpStatus.CREATED);
    }
}
