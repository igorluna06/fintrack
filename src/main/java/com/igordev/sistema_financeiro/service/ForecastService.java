package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.ForecastDTO;

public interface ForecastService {
    ForecastDTO getForecast(int monthsBack);
}
