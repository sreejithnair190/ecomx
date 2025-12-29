package me.sreejithnair.ecomx_api.product.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.product.dto.response.CategoryResponse;
import me.sreejithnair.ecomx_api.product.entity.Category;
import me.sreejithnair.ecomx_api.product.repository.CategoryRepository;
import me.sreejithnair.ecomx_api.product.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryResponse> getRootCategories() {
        return categoryRepository.findByParentIsNullAndIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<CategoryResponse> getCategoriesWithChildren() {
        return categoryRepository.findRootCategoriesWithChildren()
                .stream()
                .map(this::toResponseWithChildren)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with slug: " + slug));
        return toResponseWithChildren(category);
    }

    @Override
    public List<CategoryResponse> getSubcategories(Long parentId) {
        return categoryRepository.findByParentIdAndIsActiveTrueOrderByDisplayOrderAsc(parentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CategoryResponse toResponse(Category category) {
        CategoryResponse response = modelMapper.map(category, CategoryResponse.class);
        if (category.getParent() != null) {
            response.setParentId(category.getParent().getId());
        }
        return response;
    }

    private CategoryResponse toResponseWithChildren(Category category) {
        CategoryResponse response = toResponse(category);
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            response.setChildren(category.getChildren().stream()
                    .filter(Category::getIsActive)
                    .map(this::toResponseWithChildren)
                    .toList());
        }
        return response;
    }
}
