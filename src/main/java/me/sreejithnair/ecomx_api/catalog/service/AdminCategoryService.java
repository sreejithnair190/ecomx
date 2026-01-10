package me.sreejithnair.ecomx_api.catalog.service;

import me.sreejithnair.ecomx_api.common.dto.ToggleActiveDto;
import me.sreejithnair.ecomx_api.catalog.dto.request.CategoryRequestDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategoryDetailResponseDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategorySummaryResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminCategoryService {

    Page<CategorySummaryResponseDto> getAllCategoriesPaginated(
            Integer page,
            Integer perPage,
            String sortBy,
            String sortDir,
            Integer isActive,
            Long parentId,
            String search
    );

    CategoryDetailResponseDto getCategoryById(Long id);

    CategoryDetailResponseDto createCategory(CategoryRequestDto request);

    CategoryDetailResponseDto updateCategory(Long id, CategoryRequestDto request);

    void deleteCategory(Long id);

    void changeCategoryStatus(Long id, ToggleActiveDto toggleActiveDto);

    List<CategorySummaryResponseDto> getChildCategories(Long parentId);
}
