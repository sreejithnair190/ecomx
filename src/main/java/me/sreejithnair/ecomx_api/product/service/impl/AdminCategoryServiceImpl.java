package me.sreejithnair.ecomx_api.product.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.product.dto.request.CategoryRequest;
import me.sreejithnair.ecomx_api.product.dto.response.CategoryResponse;
import me.sreejithnair.ecomx_api.product.entity.Category;
import me.sreejithnair.ecomx_api.product.repository.CategoryRepository;
import me.sreejithnair.ecomx_api.product.service.AdminCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = findById(id);
        return toResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = modelMapper.map(request, Category.class);

        if (request.getParentId() != null) {
            Category parent = findById(request.getParentId());
            category.setParent(parent);
        }

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = findById(id);

        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        category.setDisplayOrder(request.getDisplayOrder());
        category.setIsActive(request.getIsActive());
        category.setMetaTitle(request.getMetaTitle());
        category.setMetaDescription(request.getMetaDescription());

        if (request.getParentId() != null) {
            Category parent = findById(request.getParentId());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        Category updated = categoryRepository.save(category);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    private Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    private CategoryResponse toResponse(Category category) {
        CategoryResponse response = modelMapper.map(category, CategoryResponse.class);
        if (category.getParent() != null) {
            response.setParentId(category.getParent().getId());
        }
        return response;
    }
}
