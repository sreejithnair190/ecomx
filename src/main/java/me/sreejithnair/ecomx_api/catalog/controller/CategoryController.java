package me.sreejithnair.ecomx_api.catalog.controller;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategoryDetailResponseDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategorySummaryResponseDto;
import me.sreejithnair.ecomx_api.catalog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategorySummaryResponseDto>>> getRootCategories() {
        List<CategorySummaryResponseDto> categories = categoryService.getRootCategories();
        return ApiResponse.ok(categories);
    }

    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<CategoryDetailResponseDto>>> getCategoriesWithChildren() {
        List<CategoryDetailResponseDto> categories = categoryService.getCategoriesWithChildren();
        return ApiResponse.ok(categories);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<CategoryDetailResponseDto>> getCategoryBySlug(@PathVariable String slug) {
        CategoryDetailResponseDto category = categoryService.getCategoryBySlug(slug);
        return ApiResponse.ok(category);
    }

    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<ApiResponse<List<CategorySummaryResponseDto>>> getSubcategories(@PathVariable Long parentId) {
        List<CategorySummaryResponseDto> categories = categoryService.getSubcategories(parentId);
        return ApiResponse.ok(categories);
    }
}
