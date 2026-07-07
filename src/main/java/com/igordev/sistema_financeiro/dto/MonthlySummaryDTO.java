package com.igordev.sistema_financeiro.dto;

import java.math.BigDecimal;

public record MonthlySummaryDTO(
        int month,
        int year,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal totalFixedExpense,
        BigDecimal totalVariableExpense,
        BigDecimal balance
) {
}
