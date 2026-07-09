package com.igordev.sistema_financeiro.controller;

import com.igordev.sistema_financeiro.dto.ForecastDTO;
import com.igordev.sistema_financeiro.service.ForecastService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/forecast")
@AllArgsConstructor
public class ForecastController {

    private final ForecastService forecastService;

    @GetMapping
    public ResponseEntity<ForecastDTO> getForecast(@RequestParam int monthsBack) {
        return ResponseEntity.status(HttpStatus.OK).body(this.forecastService.getForecast(monthsBack));
    }

}
