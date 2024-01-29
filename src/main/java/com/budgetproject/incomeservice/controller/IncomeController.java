package com.budgetproject.incomeservice.controller;

import com.budgetproject.incomeservice.model.IncomeRequest;
import com.budgetproject.incomeservice.model.IncomeResponse;
import com.budgetproject.incomeservice.service.IncomeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @RolesAllowed({"B_Administrator","B_Regular_Customer"})
    @PostMapping
    public ResponseEntity<Long> addIncome(@RequestBody @Valid IncomeRequest incomeRequest) {
        long incomeId = incomeService.recordIncome(incomeRequest);
        return new ResponseEntity<>(incomeId, HttpStatus.CREATED);
    }

    @RolesAllowed({"B_Administrator","B_Regular_Customer", "B_Auditor"})
    @GetMapping
    public ResponseEntity<List<IncomeResponse>> getAllIncome() {
        List<IncomeResponse> incomeResponse = incomeService.getAllIncome();
        return new ResponseEntity<>(incomeResponse, HttpStatus.OK);
    }

    @RolesAllowed({"B_Administrator","B_Regular_Customer", "B_Auditor"})
    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponse> getIncomeById(@PathVariable("id") long incomeId) {
        IncomeResponse incomeResponse
                = incomeService.getIncomeById(incomeId);
        return new ResponseEntity<>(incomeResponse, HttpStatus.OK);
    }

    @RolesAllowed({"B_Administrator","B_Regular_Customer"})
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIncome(@PathVariable("id") long incomeId,
                                             @RequestBody @Valid IncomeRequest incomeRequest) {
        incomeService.updateIncome(incomeId, incomeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RolesAllowed({"B_Administrator","B_Regular_Customer"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable("id") long incomeId) {
        incomeService.deleteIncome(incomeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
