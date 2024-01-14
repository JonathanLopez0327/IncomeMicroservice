package com.budgetproject.incomeservice.service;

import com.budgetproject.incomeservice.model.CategoryRequest;
import com.budgetproject.incomeservice.model.CategoryResponse;

import java.util.List;

public interface IncomeCategoryService {
    long recordCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> listCategory();

    void modifyCategory(long categoryId, CategoryRequest categoryRequest);

    void removeCategory(long categoryId);
}
