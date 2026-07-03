package com.igordev.sistema_financeiro.repository;

import com.igordev.sistema_financeiro.enums.TransactionNature;
import com.igordev.sistema_financeiro.enums.TransactionType;
import com.igordev.sistema_financeiro.model.Category;
import com.igordev.sistema_financeiro.model.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCategory(Category category);
    List<Transaction> findByNature(TransactionNature nature);
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByAmount(BigDecimal amount, Sort sort);
    List<Transaction> findByAmountLessThanEqual(BigDecimal amount, Sort sort);
    List<Transaction> findByAmountGreaterThanEqual(BigDecimal amount, Sort sort);
    List<Transaction> findByAmountBetween(BigDecimal min, BigDecimal max, Sort sort);
    List<Transaction> findByDate(LocalDate date, Sort sort);
    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate, Sort sort);
    List<Transaction> findByDateLessThanEqual(LocalDate date, Sort sort);
    List<Transaction> findByDateGreaterThanEqual(LocalDate date, Sort sort);
    @Query("SELECT t FROM Transaction t WHERE MONTH(t.date) = :month AND YEAR(t.date) = :year")
    List<Transaction> findByMonthAndYear(@Param("month") int month, @Param("year") int year, Sort sort);
}
