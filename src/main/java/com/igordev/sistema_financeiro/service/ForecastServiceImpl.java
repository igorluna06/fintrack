package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.ForecastDTO;
import com.igordev.sistema_financeiro.enums.TransactionType;
import com.igordev.sistema_financeiro.exception.BusinessException;
import com.igordev.sistema_financeiro.exception.message.ExceptionMessages;
import com.igordev.sistema_financeiro.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ForecastServiceImpl implements ForecastService {

    private final TransactionService transactionService;

    @Override
    public ForecastDTO getForecast(int monthsBack) {
        if (monthsBack < 1)
            throw new BusinessException(ExceptionMessages.MONTHS_BACK_INVALID);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (int i = monthsBack; i >= 1; i--) {
            LocalDate date = LocalDate.now().minusMonths(i);
            int month = date.getMonthValue();
            int year = date.getYear();

            List<Transaction> transactions = this.transactionService.findByMonthAndYear(month, year, Sort.unsorted());

            totalIncome = totalIncome.add(transactions.stream()
                    .filter(t -> t.getType() == TransactionType.INCOME)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            totalExpense = totalExpense.add(transactions.stream()
                    .filter(t -> t.getType() == TransactionType.EXPENSE)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        }

        BigDecimal months = BigDecimal.valueOf(monthsBack);
        BigDecimal predictedIncome = totalIncome.divide(months, 2, RoundingMode.HALF_UP);
        BigDecimal predictedExpense = totalExpense.divide(months, 2, RoundingMode.HALF_UP);
        BigDecimal predictedBalance = predictedIncome.subtract(predictedExpense);

        LocalDate nextMonth = LocalDate.now().plusMonths(1);

        return new ForecastDTO(
                nextMonth.getMonthValue(),
                nextMonth.getYear(),
                predictedIncome,
                predictedExpense,
                predictedBalance
        );
    }
}
