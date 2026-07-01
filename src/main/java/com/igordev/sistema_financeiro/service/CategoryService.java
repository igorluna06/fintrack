package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.model.Category;
import com.igordev.sistema_financeiro.enums.CategoryType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category create(Category category);
    Category update(Long id, Category category);
    Category findById(Long id);
    Category findByName(String name);
    List<Category> findByCategoryType(CategoryType categoryType);
    List<Category> findAll();
    Category updateName(Long id, String name);
    Category updateType(Long id, CategoryType type);
    void delete(Long id);
}
