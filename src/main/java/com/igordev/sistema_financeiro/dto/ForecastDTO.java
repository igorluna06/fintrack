package com.igordev.sistema_financeiro.dto;

import java.math.BigDecimal;

public record ForecastDTO(
        int referencedMonth,
        int referencedYear,
        BigDecimal predictedIncome,
        BigDecimal predictedExpense,
        BigDecimal predictedBalance
) {}
