package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.*;
import com.igordev.sistema_financeiro.enums.TransactionNature;
import com.igordev.sistema_financeiro.exception.BusinessException;
import com.igordev.sistema_financeiro.exception.message.ExceptionMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final TransactionService transactionService;
    private final SummaryService summaryService;
    private final ForecastService forecastService;

    @Override
    public List<RecommendationDTO> getRecommendations(int month, int year, int monthsBack) {
        if (month < 1 || month > 12)
            throw new BusinessException(ExceptionMessages.TRANSACTION_MONTH_INVALID);
        if (year < 2000 || year > LocalDate.now().getYear())
            throw new BusinessException(ExceptionMessages.TRANSACTION_YEAR_INVALID);
        if (monthsBack < 1)
            throw new BusinessException(ExceptionMessages.MONTHS_BACK_INVALID);

        List<RecommendationDTO> recommendations = new ArrayList<>();

        MonthlySummaryDTO current = summaryService.getMonthlySummary(month, year);
        ForecastDTO forecast = forecastService.getForecast(monthsBack);

        recommendations.addAll(buildTotalComparison(current, forecast));
        recommendations.addAll(buildCategoryComparisons(month, year, monthsBack));
        recommendations.addAll(buildNatureComparison(month, year, monthsBack));
        recommendations.add(buildHighestExpense(month, year));
        recommendations.add(buildBestMonth(monthsBack));

        return recommendations.stream()
                .filter(r -> r != null)
                .toList();
    }

    private List<RecommendationDTO> buildTotalComparison(MonthlySummaryDTO current, ForecastDTO forecast) {
        List<RecommendationDTO> list = new ArrayList<>();

        if (forecast.predictedExpense().compareTo(BigDecimal.ZERO) > 0) {
            double percentageChange = current.totalExpense()
                    .subtract(forecast.predictedExpense())
                    .divide(forecast.predictedExpense(), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue();

            String message = percentageChange > 0
                    ? String.format("Seu gasto total este mês está %.1f%% acima da média histórica", percentageChange)
                    : String.format("Seu gasto total este mês está %.1f%% abaixo da média histórica", Math.abs(percentageChange));

            list.add(new RecommendationDTO("TOTAL", message, current.totalExpense(), forecast.predictedExpense(), percentageChange));
        }

        return list;
    }

    private List<RecommendationDTO> buildCategoryComparisons(int month, int year, int monthsBack) {
        List<CategoryExpenseDTO> current = summaryService.getExpensesByCategory(month, year);

        Map<String, BigDecimal> historicalAvg = new HashMap<>();
        for (int i = monthsBack; i >= 1; i--) {
            LocalDate date = LocalDate.now().minusMonths(i);
            List<CategoryExpenseDTO> historical = summaryService.getExpensesByCategory(date.getMonthValue(), date.getYear());
            historical.forEach(c -> historicalAvg.merge(c.categoryName(), c.totalAmount(), BigDecimal::add));
        }
        historicalAvg.replaceAll((k, v) -> v.divide(BigDecimal.valueOf(monthsBack), 2, RoundingMode.HALF_UP));

        return current.stream()
                .filter(c -> historicalAvg.containsKey(c.categoryName()))
                .map(c -> {
                    BigDecimal avg = historicalAvg.get(c.categoryName());
                    double pct = c.totalAmount()
                            .subtract(avg)
                            .divide(avg, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .doubleValue();
                    String msg = pct > 0
                            ? String.format("Seus gastos em %s estão %.1f%% acima da média", c.categoryName(), pct)
                            : String.format("Seus gastos em %s estão %.1f%% abaixo da média", c.categoryName(), Math.abs(pct));
                    return new RecommendationDTO("CATEGORY", msg, c.totalAmount(), avg, pct);
                })
                .toList();
    }

    private List<RecommendationDTO> buildNatureComparison(int month, int year, int monthsBack) {
        List<ExpenseByNatureDTO> current = summaryService.getExpensesByNature(month, year);

        Map<TransactionNature, BigDecimal> historicalAvg = new HashMap<>();
        for (int i = monthsBack; i >= 1; i--) {
            LocalDate date = LocalDate.now().minusMonths(i);
            List<ExpenseByNatureDTO> historical = summaryService.getExpensesByNature(date.getMonthValue(), date.getYear());
            historical.forEach(n -> historicalAvg.merge(n.nature(), n.totalAmount(), BigDecimal::add));
        }
        historicalAvg.replaceAll((k, v) -> v.divide(BigDecimal.valueOf(monthsBack), 2, RoundingMode.HALF_UP));

        return current.stream()
                .filter(n -> historicalAvg.containsKey(n.nature()))
                .map(n -> {
                    BigDecimal avg = historicalAvg.get(n.nature());
                    double pct = n.totalAmount()
                            .subtract(avg)
                            .divide(avg, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .doubleValue();
                    String natureName = n.nature() == TransactionNature.FIXED ? "fixos" : "variáveis";
                    String msg = pct > 0
                            ? String.format("Seus gastos %s estão %.1f%% acima da média", natureName, pct)
                            : String.format("Seus gastos %s estão %.1f%% abaixo da média", natureName, Math.abs(pct));
                    return new RecommendationDTO("NATURE", msg, n.totalAmount(), avg, pct);
                })
                .toList();
    }

    private RecommendationDTO buildHighestExpense(int month, int year) {
        List<CategoryExpenseDTO> expenses = summaryService.getExpensesByCategory(month, year);

        return expenses.stream()
                .max(Comparator.comparing(CategoryExpenseDTO::totalAmount))
                .map(c -> new RecommendationDTO(
                        "HIGHEST_EXPENSE",
                        String.format("Sua maior despesa este mês foi em %s com R$ %.2f", c.categoryName(), c.totalAmount()),
                        c.totalAmount(),
                        null,
                        null
                ))
                .orElse(null);
    }

    private RecommendationDTO buildBestMonth(int monthsBack) {
        MonthlyEvolutionDTO best = null;

        for (int i = monthsBack; i >= 1; i--) {
            LocalDate date = LocalDate.now().minusMonths(i);
            List<MonthlyEvolutionDTO> evolution = summaryService.getMonthlyEvolution(monthsBack);
            best = evolution.stream()
                    .min(Comparator.comparing(MonthlyEvolutionDTO::totalExpense))
                    .orElse(null);
            break;
        }

        if (best == null) return null;

        return new RecommendationDTO(
                "BEST_MONTH",
                String.format("Seu melhor mês foi %02d/%d com R$ %.2f em gastos", best.month(), best.year(), best.totalExpense()),
                null,
                best.totalExpense(),
                null
        );
    }
}
