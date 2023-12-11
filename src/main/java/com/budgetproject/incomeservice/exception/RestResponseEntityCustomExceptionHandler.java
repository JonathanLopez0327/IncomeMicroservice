package com.budgetproject.incomeservice.exception;

import com.budgetproject.incomeservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityCustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncomeServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handlerIncomeServiceException(IncomeServiceCustomException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build(), HttpStatus.NOT_FOUND
        );
    }
}
