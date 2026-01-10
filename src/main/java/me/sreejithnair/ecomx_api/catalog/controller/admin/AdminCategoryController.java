 package me.sreejithnair.ecomx_api.catalog.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.common.dto.PagedResponse;
import me.sreejithnair.ecomx_api.common.dto.ToggleActiveDto;
import me.sreejithnair.ecomx_api.catalog.dto.request.CategoryRequestDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategoryDetailResponseDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategorySummaryResponseDto;
import me.sreejithnair.ecomx_api.catalog.service.AdminCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<CategorySummaryResponseDto>>> getAllCategories(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage,
            @RequestParam(defaultValue = "displayOrder") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Integer isActive,
            @RequestParam(required = false) Long parentId,
            @RequestParam(required = false) String search
    ) {
        Page<CategorySummaryResponseDto> categories = adminCategoryService.getAllCategoriesPaginated(
                page, perPage, sortBy, sortDir, isActive, parentId, search
        );
        return ApiResponse.ok(PagedResponse.from(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailResponseDto>> getCategoryById(@PathVariable Long id) {
        CategoryDetailResponseDto category = adminCategoryService.getCategoryById(id);
        return ApiResponse.ok(category);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDetailResponseDto>> createCategory(@Valid @ModelAttribute CategoryRequestDto request) {
        CategoryDetailResponseDto category = adminCategoryService.createCategory(request);
        return ApiResponse.created(category, "Category created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailResponseDto>> updateCategory(
            @PathVariable Long id,
            @Valid @ModelAttribute CategoryRequestDto request
    ) {
        CategoryDetailResponseDto category = adminCategoryService.updateCategory(id, request);
        return ApiResponse.ok(category, "Category updated successfully!");
    }

    @PatchMapping("/change-status/{id}")
    public ResponseEntity<ApiResponse<String>> changeCategoryStatus(
            @PathVariable Long id,
            @RequestBody ToggleActiveDto toggleActiveDto
    ) {
        adminCategoryService.changeCategoryStatus(id, toggleActiveDto);
        return ApiResponse.ok("Category status changed successfully");
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<ApiResponse<List<CategorySummaryResponseDto>>> getChildCategories(@PathVariable Long id) {
        List<CategorySummaryResponseDto> children = adminCategoryService.getChildCategories(id);
        return ApiResponse.ok(children);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        adminCategoryService.deleteCategory(id);
        return ApiResponse.noContent();
    }
}
