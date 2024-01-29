package com.budgetproject.incomeservice.controller;

import com.budgetproject.incomeservice.model.CategoryRequest;
import com.budgetproject.incomeservice.model.CategoryResponse;
import com.budgetproject.incomeservice.service.IncomeCategoryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income/category")
public class IncomeCategoryController {

    @Autowired
    private IncomeCategoryService categoryService;

    @RolesAllowed({"B_Administrator","B_Regular_Customer"})
    @PostMapping
    public ResponseEntity<Long> recordCategory(@RequestBody CategoryRequest categoryRequest) {
        long categoryId = categoryService.recordCategory(categoryRequest);
        return new ResponseEntity<>(categoryId, HttpStatus.CREATED);
    }

    @RolesAllowed({"B_Administrator","B_Regular_Customer", "B_Auditor"})
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> listCategory() {
        List<CategoryResponse> responses = categoryService.listCategory();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @RolesAllowed({"B_Administrator","B_Regular_Customer"})
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyCategory(
            @PathVariable("id") long categoryId,
            @RequestBody CategoryRequest categoryRequest
    ) {
        categoryService.modifyCategory(categoryId, categoryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RolesAllowed({"B_Administrator","B_Regular_Customer"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCategory(
            @PathVariable("id") long categoryId
    ) {
        categoryService.removeCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
