package com.igordev.sistema_financeiro.service;

import com.igordev.sistema_financeiro.exception.BusinessException;
import com.igordev.sistema_financeiro.exception.ResourceNotFoundException;
import com.igordev.sistema_financeiro.exception.message.ExceptionMessages;
import com.igordev.sistema_financeiro.model.Category;
import com.igordev.sistema_financeiro.enums.CategoryType;
import com.igordev.sistema_financeiro.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        if(category.getName() == null || category.getName().isBlank()){
            throw new BusinessException(ExceptionMessages.CATEGORY_NAME_REQUIRED);
        }
        if (category.getType() == null)
            throw new BusinessException(ExceptionMessages.CATEGORY_TYPE_REQUIRED);
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateName(Long id, String name) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND));
        if (name == null || name.isBlank())
            throw new BusinessException(ExceptionMessages.CATEGORY_NAME_REQUIRED);
        existing.setName(name);
        return categoryRepository.save(existing);
    }

    @Override
    public Category updateType(Long id, CategoryType type) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND));
        if (type == null)
            throw new BusinessException(ExceptionMessages.CATEGORY_TYPE_REQUIRED);
        existing.setType(type);
        return categoryRepository.save(existing);
    }

    @Override
    public Category update(Long id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND));
        if (category.getName() == null || category.getName().isBlank())
            throw new BusinessException(ExceptionMessages.CATEGORY_NAME_REQUIRED);
        if (category.getType() == null)
            throw new BusinessException(ExceptionMessages.CATEGORY_TYPE_REQUIRED);
        existing.setName(category.getName());
        existing.setType(category.getType());
        return categoryRepository.save(existing);
    }

    @Override
    public Category findById(Long id) {
        if(id == null)
            throw new BusinessException(ExceptionMessages.ID_REQUIRED);
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category findByName(String name) {
        if(name == null || name.isBlank())
            throw new BusinessException(ExceptionMessages.CATEGORY_NAME_REQUIRED);
        return this.categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<Category> findByCategoryType(CategoryType categoryType) {
        if(categoryType == null)
            throw new BusinessException(ExceptionMessages.CATEGORY_TYPE_REQUIRED);
        return this.categoryRepository.findByCategoryType(categoryType);
    }

    @Override
    public void delete(Long id) {
        Category existingCategory = this.findById(id);
        this.categoryRepository.delete(existingCategory);
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }
}
