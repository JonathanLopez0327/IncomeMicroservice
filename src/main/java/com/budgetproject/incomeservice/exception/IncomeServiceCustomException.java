package com.budgetproject.incomeservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IncomeServiceCustomException extends RuntimeException {
    private String errorCode;
    private int status;

    public IncomeServiceCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public IncomeServiceCustomException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
