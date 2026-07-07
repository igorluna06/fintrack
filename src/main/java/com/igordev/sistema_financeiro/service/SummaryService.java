package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.MonthlySummaryDTO;

public interface SummaryService {
    MonthlySummaryDTO getMonthlySummary(int month, int year);
}
