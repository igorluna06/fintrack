package com.igordev.sistema_financeiro.controller;

import com.igordev.sistema_financeiro.dto.RecommendationDTO;
import com.igordev.sistema_financeiro.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<List<RecommendationDTO>> getRecommendations(@RequestParam int month,
                                                                      @RequestParam int year,
                                                                      @RequestParam int monthsBack){

        return ResponseEntity.status(HttpStatus.OK).body(
                recommendationService.getRecommendations(month, year, monthsBack)
        );
    }
}
