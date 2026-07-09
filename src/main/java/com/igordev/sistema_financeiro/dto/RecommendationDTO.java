package com.igordev.sistema_financeiro.dto;

import java.math.BigDecimal;

public record RecommendationDTO(
        String type,
        String message,
        BigDecimal currentAmount,
        BigDecimal referenceAmount,
        Double percentageChange
) {}
