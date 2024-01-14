package com.budgetproject.incomeservice.controller;

import com.budgetproject.incomeservice.model.CategoryRequest;
import com.budgetproject.incomeservice.model.CategoryResponse;
import com.budgetproject.incomeservice.service.IncomeCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income/category")
public class IncomeCategoryController {

    @Autowired
    private IncomeCategoryService categoryService;

    @PostMapping
    public ResponseEntity<Long> recordCategory(@RequestBody CategoryRequest categoryRequest) {
        long categoryId = categoryService.recordCategory(categoryRequest);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> listCategory() {
        List<CategoryResponse> responses = categoryService.listCategory();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyCategory(
            @PathVariable("id") long categoryId,
            @RequestBody CategoryRequest categoryRequest
    ) {
        categoryService.modifyCategory(categoryId, categoryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCategory(
            @PathVariable("id") long categoryId
    ) {
        categoryService.removeCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
