package com.budgetproject.incomeservice.service;

import com.budgetproject.incomeservice.entity.IncomeCategory;
import com.budgetproject.incomeservice.exception.IncomeServiceCustomException;
import com.budgetproject.incomeservice.model.CategoryRequest;
import com.budgetproject.incomeservice.model.CategoryResponse;
import com.budgetproject.incomeservice.repository.IncomeCategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class IncomeCategoryServiceImpl implements IncomeCategoryService {

    @Autowired
    private IncomeCategoryRepository categoryRepository;

    @Override
    public long recordCategory(CategoryRequest categoryRequest) {
        log.info("Verifying Income Category");
        IncomeCategory incomeCategory = categoryRepository.findByName(categoryRequest.getName());

        if (incomeCategory == null) {
            log.info("Recording new Income Category");
            IncomeCategory category = IncomeCategory.builder()
                    .name(categoryRequest.getName())
                    .build();

            categoryRepository.save(category);
            log.info("Income Category Recorded Successfully!");
            return category.getCategoryId();
        }

        log.warn("Income Category with name: {} Already exist", categoryRequest.getName());
        return 0;
    }

    @Override
    public List<CategoryResponse> listCategory() {
        log.info("Getting all Categories");
        List<IncomeCategory> categories = categoryRepository.findAll();

        return categories
                .stream()
                .map(category -> {
                    CategoryResponse response = new CategoryResponse();
                    BeanUtils.copyProperties(category, response);
                    return response;
                }).toList();
    }

    @Override
    public void modifyCategory(long categoryId, CategoryRequest categoryRequest) {
        log.info("Modifying Income Category");

        IncomeCategory category =
                categoryRepository.findById(categoryId).orElseThrow(
                        () -> new IncomeServiceCustomException(
                                "Category with given Id not found",
                                "CATEGORY_NOT_FOUND",
                                404
                        ));

        if (!categoryRequest.getName().isEmpty()) {
            category.setName(categoryRequest.getName());

            IncomeCategory incomeCategory = categoryRepository.findByName(categoryRequest.getName());

            if (incomeCategory == null) {
                categoryRepository.save(category);
                log.info("Income Category Updated Successfully!");
            }
        }
    }

    @Override
    public void removeCategory(long categoryId) {
        log.info("Getting Income Category with id : {}", categoryId);

        IncomeCategory category =
                categoryRepository.findById(categoryId).orElseThrow(
                        () -> new IncomeServiceCustomException(
                                "Category with given Id not found",
                                "CATEGORY_NOT_FOUND",
                                404
                        ));
        log.info("Removing category : {}", category.getName());
        categoryRepository.delete(category);
    }
}
