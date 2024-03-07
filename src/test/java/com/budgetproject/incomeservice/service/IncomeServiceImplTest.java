package com.budgetproject.incomeservice.service;


import com.budgetproject.incomeservice.external.client.AccountService;
import com.budgetproject.incomeservice.model.IncomeResponse;
import com.budgetproject.incomeservice.repository.IncomeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import com.budgetproject.incomeservice.entity.Income;

@SpringBootTest
class IncomeServiceImplTest {
    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    IncomeService incomeService = new IncomeServiceImpl();

    @DisplayName("Get Income - Success Scenario")
    @Test
    void test_When_Income_Success() {
        //Mocking
        Income income = getMockIncome();
        when(incomeRepository.findById(anyLong()))
                .thenReturn(Optional.of(income));
        //Actual
        IncomeResponse incomeResponse = incomeService.getIncomeById(2);
        //Verification
        verify(incomeRepository, times(1)).findById(anyLong());
        //Assert
        assertNotNull(incomeResponse);
        assertEquals(income.getIncomeId(), incomeResponse.getIncomeId());
    }

    private Income getMockIncome() {
        return Income.builder()
                .accountId(1)
                .amount(100)
                .incomeDescription("Salario 1")
                .incomeType("LINEAL")
                .date(Instant.now())
                .period("202010")
                .build();
    }
}