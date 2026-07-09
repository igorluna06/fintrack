package com.igordev.sistema_financeiro.dto;

import com.igordev.sistema_financeiro.enums.TransactionNature;

import java.math.BigDecimal;

public record ExpenseByNatureDTO(
        TransactionNature nature,
        BigDecimal totalAmount
) {
}
