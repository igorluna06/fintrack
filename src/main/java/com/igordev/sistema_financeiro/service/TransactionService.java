package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.CreateTransactionDTO;
import com.igordev.sistema_financeiro.dto.UpdateTransactionDTO;
import com.igordev.sistema_financeiro.enums.TransactionNature;
import com.igordev.sistema_financeiro.enums.TransactionType;
import com.igordev.sistema_financeiro.model.Category;
import com.igordev.sistema_financeiro.model.Transaction;
import org.springframework.data.domain.Sort;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface TransactionService {
    Transaction create(CreateTransactionDTO dto);
    Transaction update(Long id, UpdateTransactionDTO dto);
    Transaction findById(long id);
    List<Transaction> findAll(Sort sort);
    List<Transaction> findByDate(LocalDate date,  Sort sort);
    List<Transaction> findByCategory(Long categoryId);
    List<Transaction> findByTransactionType(TransactionType transactionType);
    List<Transaction> findByTransactionNature(TransactionNature transactionNature);
    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate,  Sort sort);
    List<Transaction> findByDateLessThanEqual(LocalDate date,  Sort sort);
    List<Transaction> findByDateGreaterThanEqual(LocalDate date,  Sort sort);
    List<Transaction> findByAmountGreaterThanEqual(BigDecimal amount,   Sort sort);
    List<Transaction> findByAmountBetween(BigDecimal min, BigDecimal max,  Sort sort);
    List<Transaction> findByAmountLessThanEqual(BigDecimal amount,    Sort sort);
    List<Transaction> findByAmount(BigDecimal amount,  Sort sort);
    List<Transaction> findByMonthAndYear(int month, int year, Sort sort);
    void delete(Long id);
}
