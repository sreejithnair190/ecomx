package me.sreejithnair.ecomx_api.product.service;

import me.sreejithnair.ecomx_api.product.dto.request.CategoryRequest;
import me.sreejithnair.ecomx_api.product.dto.response.CategoryResponse;

import java.util.List;

public interface AdminCategoryService {

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}
