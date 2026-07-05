package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.CreateTransactionDTO;
import com.igordev.sistema_financeiro.dto.UpdateTransactionDTO;
import com.igordev.sistema_financeiro.enums.TransactionNature;
import com.igordev.sistema_financeiro.enums.TransactionType;
import com.igordev.sistema_financeiro.exception.BusinessException;
import com.igordev.sistema_financeiro.exception.ResourceNotFoundException;
import com.igordev.sistema_financeiro.exception.message.ExceptionMessages;
import com.igordev.sistema_financeiro.model.Category;
import com.igordev.sistema_financeiro.model.Transaction;
import com.igordev.sistema_financeiro.repository.TransactionRepository;
import com.igordev.sistema_financeiro.validator.TransactionValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final TransactionValidator transactionValidator;

    @Override
    public Transaction create(CreateTransactionDTO dto) {
        this.transactionValidator.validateAmount(dto.amount());
        this.transactionValidator.validateDate(dto.date());
        Category category = categoryService.findById(dto.categoryId());
        if (!category.getType().name().equals(dto.transactionType().name()))
            throw new BusinessException(ExceptionMessages.TRANSACTION_TYPE_CATEGORY_MISMATCH);
        Transaction transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setDescription(dto.description());
        transaction.setDate(dto.date());
        transaction.setAmount(dto.amount());
        transaction.setType(dto.transactionType());
        transaction.setNature(dto.transactionNature());
        return this.transactionRepository.save(transaction);
    }

    @Override
    public Transaction update(Long id, UpdateTransactionDTO dto){
        Transaction existingTransaction = this.transactionRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.TRANSACTION_NOT_FOUND));


        if (dto.categoryId() != null) {
            Category category = categoryService.findById(dto.categoryId());
            if (!category.getType().name().equals(existingTransaction.getType().name()))
                throw new BusinessException(ExceptionMessages.TRANSACTION_TYPE_CATEGORY_MISMATCH);
            existingTransaction.setCategory(category);
        }
        if(dto.description() != null){
            existingTransaction.setDescription(dto.description());
        }
        if(dto.amount() != null){
            transactionValidator.validateAmount(dto.amount());
            existingTransaction.setAmount(dto.amount());
        }
        if(dto.date() != null){
            this.transactionValidator.validateDate(dto.date());
            existingTransaction.setDate(dto.date());
        }
        if (dto.transactionType() != null) {
            if (!existingTransaction.getCategory().getType().name().equals(dto.transactionType().name()))
                throw new BusinessException(ExceptionMessages.TRANSACTION_TYPE_CATEGORY_MISMATCH);
            existingTransaction.setType(dto.transactionType());
        }
        if(dto.transactionNature() != null){
            existingTransaction.setNature(dto.transactionNature());
        }
        return this.transactionRepository.save(existingTransaction);
    }

    @Override
    public Transaction findById(long id) {
        return this.transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.TRANSACTION_NOT_FOUND));
    }

    @Override
    public List<Transaction> findAll(Sort sort) {
        return this.transactionRepository.findAll(sort);
    }

    @Override
    public List<Transaction> findByDate(LocalDate date, Sort sort) {
        this.transactionValidator.validateDate(date);
        return this.transactionRepository.findByDate(date, sort);
    }

    @Override
    public List<Transaction> findByCategory(Long categoryId) {
        Category existingCategory = this.categoryService.findById(categoryId);
        return this.transactionRepository.findByCategory(existingCategory);
    }

    @Override
    public List<Transaction> findByTransactionType(TransactionType transactionType) {
        if(transactionType == null) {
            throw new BusinessException(ExceptionMessages.TRANSACTION_TYPE_REQUIRED);
        }
        return this.transactionRepository.findByType(transactionType);
    }

    @Override
    public List<Transaction> findByTransactionNature(TransactionNature transactionNature) {
        if(transactionNature == null) {
            throw new BusinessException(ExceptionMessages.TRANSACTION_NATURE_REQUIRED);
        }
        return this.transactionRepository.findByNature(transactionNature);
    }

    @Override
    public List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate, Sort sort) {
        this.transactionValidator.validateDate(startDate);
        this.transactionValidator.validateDate(endDate);
        if (startDate.isAfter(endDate))
            throw new BusinessException(ExceptionMessages.TRANSACTION_DATE_RANGE_INVALID);
        return this.transactionRepository.findByDateBetween(startDate, endDate, sort);
    }

    @Override
    public List<Transaction> findByDateLessThanEqual(LocalDate date, Sort sort) {
        this.transactionValidator.validateDate(date);
        return this.transactionRepository.findByDateLessThanEqual(date, sort);
    }

    @Override
    public List<Transaction> findByDateGreaterThanEqual(LocalDate date, Sort sort) {
        this.transactionValidator.validateDate(date);
        return this.transactionRepository.findByDateGreaterThanEqual(date, sort);
    }

    @Override
    public List<Transaction> findByAmountGreaterThanEqual(BigDecimal amount, Sort sort) {
        this.transactionValidator.validateAmount(amount);
        return this.transactionRepository.findByAmountGreaterThanEqual(amount, sort);
    }

    @Override
    public List<Transaction> findByAmountBetween(BigDecimal min, BigDecimal max, Sort sort) {
        this.transactionValidator.validateAmount(min);
        this.transactionValidator.validateAmount(max);
        if (min.compareTo(max) > 0)
            throw new BusinessException(ExceptionMessages.TRANSACTION_AMOUNT_RANGE_INVALID);
        return this.transactionRepository.findByAmountBetween(min, max, sort);
    }

    @Override
    public List<Transaction> findByAmountLessThanEqual(BigDecimal amount, Sort sort) {
        this.transactionValidator.validateAmount(amount);
        return this.transactionRepository.findByAmountLessThanEqual(amount, sort);
    }

    @Override
    public List<Transaction> findByAmount(BigDecimal amount, Sort sort) {
        this.transactionValidator.validateAmount(amount);
        return this.transactionRepository.findByAmount(amount, sort);
    }

    @Override
    public List<Transaction> findByMonthAndYear(int month, int year, Sort sort) {
        if (month < 1 || month > 12)
            throw new BusinessException(ExceptionMessages.TRANSACTION_MONTH_INVALID);
        if (year < 2000 || year > LocalDate.now().getYear())
            throw new BusinessException(ExceptionMessages.TRANSACTION_YEAR_INVALID);

        return this.transactionRepository.findByMonthAndYear(month, year, sort);
    }

    @Override
    public void delete(Long id) {
        if(id == null) {
            throw new BusinessException(ExceptionMessages.ID_REQUIRED);
        }
        Transaction existingTransaction = this.findById(id);
        this.transactionRepository.delete(existingTransaction);
    }
}


