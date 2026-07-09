package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.CategoryExpenseDTO;
import com.igordev.sistema_financeiro.dto.ExpenseByNatureDTO;
import com.igordev.sistema_financeiro.dto.MonthlyEvolutionDTO;
import com.igordev.sistema_financeiro.dto.MonthlySummaryDTO;

import java.util.List;

public interface SummaryService {
    MonthlySummaryDTO getMonthlySummary(int month, int year);
    List<CategoryExpenseDTO> getExpensesByCategory(int month, int year);
    List<MonthlyEvolutionDTO> getMonthlyEvolution(int monthsBack);
    List<ExpenseByNatureDTO> getExpensesByNature(int month, int year);
}
