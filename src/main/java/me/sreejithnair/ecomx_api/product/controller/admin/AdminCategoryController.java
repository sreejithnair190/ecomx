package me.sreejithnair.ecomx_api.product.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.product.dto.request.CategoryRequest;
import me.sreejithnair.ecomx_api.product.dto.response.CategoryResponse;
import me.sreejithnair.ecomx_api.product.service.AdminCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = adminCategoryService.getAllCategories();
        return ApiResponse.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = adminCategoryService.getCategoryById(id);
        return ApiResponse.ok(category);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse category = adminCategoryService.createCategory(request);
        return ApiResponse.created(category, "Category created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        CategoryResponse category = adminCategoryService.updateCategory(id, request);
        return ApiResponse.ok(category, "Category updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
        return ApiResponse.noContent();
    }
}
