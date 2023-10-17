package com.budgetproject.incomeservice.repository;

import com.budgetproject.incomeservice.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepositoy extends JpaRepository<Income, Long> {
}
