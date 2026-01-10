package me.sreejithnair.ecomx_api.catalog.service;

import me.sreejithnair.ecomx_api.catalog.dto.response.CategoryDetailResponseDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategorySummaryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategorySummaryResponseDto> getRootCategories();

    List<CategoryDetailResponseDto> getCategoriesWithChildren();

    CategoryDetailResponseDto getCategoryBySlug(String slug);

    List<CategorySummaryResponseDto> getSubcategories(Long parentId);
}
