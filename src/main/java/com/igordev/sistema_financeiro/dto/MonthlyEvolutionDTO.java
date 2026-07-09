package com.igordev.sistema_financeiro.dto;

import java.math.BigDecimal;

public record MonthlyEvolutionDTO(
        int month,
        int year,
        BigDecimal totalIncome,
        BigDecimal totalExpense
) {
}
