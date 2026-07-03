package com.igordev.sistema_financeiro.controller;

import com.igordev.sistema_financeiro.dto.CreateTransactionDTO;
import com.igordev.sistema_financeiro.dto.UpdateTransactionDTO;
import com.igordev.sistema_financeiro.enums.TransactionNature;
import com.igordev.sistema_financeiro.enums.TransactionType;
import com.igordev.sistema_financeiro.model.Transaction;
import com.igordev.sistema_financeiro.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody @Valid CreateTransactionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id, @RequestBody UpdateTransactionDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll(
            @RequestParam(defaultValue = "date") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAll(sort));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Transaction>> findByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByCategory(categoryId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> findByType(@PathVariable TransactionType type) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByTransactionType(type));
    }

    @GetMapping("/nature/{nature}")
    public ResponseEntity<List<Transaction>> findByNature(@PathVariable TransactionNature nature) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByTransactionNature(nature));
    }

    @GetMapping("/date")
    public ResponseEntity<List<Transaction>> findByDate(
            @RequestParam LocalDate date,
            @RequestParam(defaultValue = "amount") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByDate(date, sort));
    }

    @GetMapping("/date/between")
    public ResponseEntity<List<Transaction>> findByDateBetween(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "amount") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByDateBetween(startDate, endDate, sort));
    }

    @GetMapping("/date/before")
    public ResponseEntity<List<Transaction>> findByDateLessThanEqual(
            @RequestParam LocalDate date,
            @RequestParam(defaultValue = "amount") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByDateLessThanEqual(date, sort));
    }

    @GetMapping("/date/after")
    public ResponseEntity<List<Transaction>> findByDateGreaterThanEqual(
            @RequestParam LocalDate date,
            @RequestParam(defaultValue = "amount") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByDateGreaterThanEqual(date, sort));
    }

    @GetMapping("/date/month")
    public ResponseEntity<List<Transaction>> findByMonthAndYear(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam(defaultValue = "amount") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByMonthAndYear(month, year, sort));
    }

    @GetMapping("/amount")
    public ResponseEntity<List<Transaction>> findByAmount(
            @RequestParam BigDecimal amount,
            @RequestParam(defaultValue = "date") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByAmount(amount, sort));
    }

    @GetMapping("/amount/between")
    public ResponseEntity<List<Transaction>> findByAmountBetween(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max,
            @RequestParam(defaultValue = "date") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByAmountBetween(min, max, sort));
    }

    @GetMapping("/amount/less")
    public ResponseEntity<List<Transaction>> findByAmountLessThanEqual(
            @RequestParam BigDecimal amount,
            @RequestParam(defaultValue = "date") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByAmountLessThanEqual(amount, sort));
    }

    @GetMapping("/amount/greater")
    public ResponseEntity<List<Transaction>> findByAmountGreaterThanEqual(
            @RequestParam BigDecimal amount,
            @RequestParam(defaultValue = "date") String orderBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = buildSort(orderBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByAmountGreaterThanEqual(amount, sort));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Sort buildSort(String orderBy, String direction) {
        return direction.equalsIgnoreCase("desc")
                ? Sort.by(orderBy).descending()
                : Sort.by(orderBy).ascending();
    }
}