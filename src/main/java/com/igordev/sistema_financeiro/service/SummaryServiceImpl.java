package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.CategoryExpenseDTO;
import com.igordev.sistema_financeiro.dto.ExpenseByNatureDTO;
import com.igordev.sistema_financeiro.dto.MonthlyEvolutionDTO;
import com.igordev.sistema_financeiro.dto.MonthlySummaryDTO;
import com.igordev.sistema_financeiro.enums.TransactionNature;
import com.igordev.sistema_financeiro.enums.TransactionType;
import com.igordev.sistema_financeiro.exception.BusinessException;
import com.igordev.sistema_financeiro.exception.message.ExceptionMessages;
import com.igordev.sistema_financeiro.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final TransactionService transactionService;

    @Override
    public MonthlySummaryDTO getMonthlySummary(int month, int year) {
        this.validateMonthAndYear(month, year);
        List<Transaction> transactions = this.transactionService.findByMonthAndYear(
                month,
                year,
                Sort.unsorted()
        );
        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalFixedExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .filter(t -> t.getNature() == TransactionNature.FIXED)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalVariableExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .filter(t -> t.getNature() == TransactionNature.VARIABLE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new MonthlySummaryDTO(
                month,
                year,
                totalIncome,
                totalExpense,
                totalFixedExpense,
                totalVariableExpense,
                balance
        );
    }

    @Override
    public List<CategoryExpenseDTO> getExpensesByCategory(int month, int year) {
        this.validateMonthAndYear(month, year);

        List<Transaction> transactions = transactionService.findByMonthAndYear(month, year, Sort.unsorted());

        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ))
                .entrySet().stream()
                .map(e -> new CategoryExpenseDTO(e.getKey(), e.getValue()))
                .toList();
    }


    @Override
    public List<MonthlyEvolutionDTO> getMonthlyEvolution(int monthsBack) {
        if (monthsBack < 1)
            throw new BusinessException(ExceptionMessages.MONTHS_BACK_INVALID);

        List<MonthlyEvolutionDTO> evolution = new ArrayList<>();

        for (int i = monthsBack - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusMonths(i);
            int month = date.getMonthValue();
            int year = date.getYear();

            List<Transaction> transactions = transactionService.findByMonthAndYear(month, year, Sort.unsorted());

            BigDecimal totalIncome = transactions.stream()
                    .filter(t -> t.getType() == TransactionType.INCOME)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalExpense = transactions.stream()
                    .filter(t -> t.getType() == TransactionType.EXPENSE)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            evolution.add(new MonthlyEvolutionDTO(month, year, totalIncome, totalExpense));
        }

        return evolution;
    }

    @Override
    public List<ExpenseByNatureDTO> getExpensesByNature(int month, int year) {
        this.validateMonthAndYear(month, year);

        List<Transaction> transactions = transactionService.findByMonthAndYear(month, year, Sort.unsorted());

        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        t -> t.getNature(),
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ))
                .entrySet().stream()
                .map(e -> new ExpenseByNatureDTO(e.getKey(), e.getValue()))
                .toList();
    }

    private void validateMonthAndYear(int month, int year) {
        if (month < 1 || month > 12)
            throw new BusinessException(ExceptionMessages.TRANSACTION_MONTH_INVALID);
        if (year < 2000 || year > LocalDate.now().getYear())
            throw new BusinessException(ExceptionMessages.TRANSACTION_YEAR_INVALID);
    }
}
