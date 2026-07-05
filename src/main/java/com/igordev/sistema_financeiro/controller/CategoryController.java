package com.igordev.sistema_financeiro.controller;

import com.igordev.sistema_financeiro.enums.CategoryType;
import com.igordev.sistema_financeiro.model.Category;
import com.igordev.sistema_financeiro.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.categoryService.create(category)
        );
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Category> updateName(@PathVariable Long id, @RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.categoryService.updateName(id, name)
        );
    }

    @PatchMapping("/{id}/type")
    public ResponseEntity<Category> updateType(@PathVariable Long id, @RequestParam CategoryType type) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.categoryService.updateType(id, type)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.categoryService.update(id, category)
        );
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.categoryService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.categoryService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Category> findByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.categoryService.findByName(name)
        );
    }

    @GetMapping("/type")
    public ResponseEntity<List<Category>> findByCategoryType(@RequestParam CategoryType type) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.categoryService.findByType(type)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
