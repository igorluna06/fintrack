package com.igordev.sistema_financeiro.dto;

import java.math.BigDecimal;

public record CategoryExpenseDTO(
        String categoryName,
        BigDecimal totalAmount
) {
}
