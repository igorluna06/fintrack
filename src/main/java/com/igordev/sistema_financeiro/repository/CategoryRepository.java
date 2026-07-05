package com.igordev.sistema_financeiro.repository;

import com.igordev.sistema_financeiro.model.Category;
import com.igordev.sistema_financeiro.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findByType(CategoryType type);
}
