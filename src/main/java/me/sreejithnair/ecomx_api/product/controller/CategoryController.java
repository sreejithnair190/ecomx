package me.sreejithnair.ecomx_api.product.controller;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.product.dto.response.CategoryResponse;
import me.sreejithnair.ecomx_api.product.service.CategoryService;
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
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getRootCategories() {
        List<CategoryResponse> categories = categoryService.getRootCategories();
        return ApiResponse.ok(categories);
    }

    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoriesWithChildren() {
        List<CategoryResponse> categories = categoryService.getCategoriesWithChildren();
        return ApiResponse.ok(categories);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryBySlug(@PathVariable String slug) {
        CategoryResponse category = categoryService.getCategoryBySlug(slug);
        return ApiResponse.ok(category);
    }

    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getSubcategories(@PathVariable Long parentId) {
        List<CategoryResponse> categories = categoryService.getSubcategories(parentId);
        return ApiResponse.ok(categories);
    }
}
