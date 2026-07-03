package com.igordev.sistema_financeiro.dto;

import com.igordev.sistema_financeiro.enums.TransactionNature;
import com.igordev.sistema_financeiro.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionDTO(

        @NotNull(message = "Categoria é obrigatória")
        Long categoryId,

        String description,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser positivo")
        BigDecimal amount,

        @NotNull(message = "Data é obrigatória")
        LocalDate date,

        @NotNull(message = "Tipo é obrigatório")
        TransactionType transactionType,

        @NotNull(message = "Natureza é obrigatória")
        TransactionNature transactionNature

) {}
