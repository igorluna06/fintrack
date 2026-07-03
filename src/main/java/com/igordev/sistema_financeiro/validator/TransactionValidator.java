package com.igordev.sistema_financeiro.validator;

import com.igordev.sistema_financeiro.exception.BusinessException;
import com.igordev.sistema_financeiro.exception.message.ExceptionMessages;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionValidator {

    public void validateAmount(BigDecimal amount) {
        if (amount == null)
            throw new BusinessException(ExceptionMessages.TRANSACTION_AMOUNT_REQUIRED);
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException(ExceptionMessages.TRANSACTION_AMOUNT_INVALID);
    }

    public void validateDate(LocalDate date) {
        if (date == null)
            throw new BusinessException(ExceptionMessages.TRANSACTION_DATE_REQUIRED);
        if (date.isAfter(LocalDate.now()))
            throw new BusinessException(ExceptionMessages.TRANSACTION_DATE_INVALID);
    }
}
