package com.igordev.sistema_financeiro.dto;


import com.igordev.sistema_financeiro.enums.TransactionNature;
import com.igordev.sistema_financeiro.enums.TransactionType;
import com.igordev.sistema_financeiro.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateTransactionDTO(
        Long categoryId,
        String description,
        BigDecimal amount,
        LocalDate date,
        TransactionType transactionType,
        TransactionNature transactionNature
) {}
