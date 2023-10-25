package com.budgetproject.incomeservice.controller;

import com.budgetproject.incomeservice.model.IncomeRequest;
import com.budgetproject.incomeservice.model.IncomeResponse;
import com.budgetproject.incomeservice.service.IncomeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income")
@Log4j2
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Long> addIncome(@RequestBody IncomeRequest incomeRequest) {
        long incomeId = incomeService.recordIncome(incomeRequest);
        return new ResponseEntity<>(incomeId, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IncomeResponse>> getAllIncome() {
        List<IncomeResponse> incomeResponse = incomeService.getAllIncome();
        return new ResponseEntity<>(incomeResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponse> getIncomeById(@PathVariable("id") long incomeId) {
        IncomeResponse incomeResponse
                = incomeService.getIncomeById(incomeId);
        return new ResponseEntity<>(incomeResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> setIncome(@PathVariable("id") long incomeId,
                                          @RequestBody IncomeRequest incomeRequest) {
        incomeService.updateIncome(incomeId, incomeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
