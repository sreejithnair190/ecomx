package me.sreejithnair.ecomx_api.product.service;

import me.sreejithnair.ecomx_api.product.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getRootCategories();

    List<CategoryResponse> getCategoriesWithChildren();

    CategoryResponse getCategoryBySlug(String slug);

    List<CategoryResponse> getSubcategories(Long parentId);
}
