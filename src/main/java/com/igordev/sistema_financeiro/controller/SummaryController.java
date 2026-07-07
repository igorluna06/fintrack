package com.igordev.sistema_financeiro.controller;

import com.igordev.sistema_financeiro.dto.MonthlySummaryDTO;
import com.igordev.sistema_financeiro.service.SummaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summary")
@AllArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping
    public ResponseEntity<MonthlySummaryDTO> getMonthlySummary(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.status(HttpStatus.OK).body(summaryService.getMonthlySummary(month, year));
    }
}
