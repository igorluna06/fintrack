package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.dto.RecommendationDTO;

import java.util.List;

public interface RecommendationService {
    List<RecommendationDTO> getRecommendations(int month, int year, int monthsBack);
}
