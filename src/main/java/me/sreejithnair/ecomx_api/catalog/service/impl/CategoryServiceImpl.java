package me.sreejithnair.ecomx_api.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;
import me.sreejithnair.ecomx_api.asset.entity.Asset;
import me.sreejithnair.ecomx_api.common.dto.SeoMetadataResponse;
import me.sreejithnair.ecomx_api.common.embeddable.SeoMetadata;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategoryDetailResponseDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategorySummaryResponseDto;
import me.sreejithnair.ecomx_api.catalog.entity.Category;
import me.sreejithnair.ecomx_api.catalog.repository.CategoryRepository;
import me.sreejithnair.ecomx_api.catalog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategorySummaryResponseDto> getRootCategories() {
        return categoryRepository.findByParentIsNullAndIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    @Override
    public List<CategoryDetailResponseDto> getCategoriesWithChildren() {
        return categoryRepository.findRootCategoriesWithChildren()
                .stream()
                .map(this::toDetailResponseWithChildren)
                .toList();
    }

    @Override
    public CategoryDetailResponseDto getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with slug: " + slug));
        return toDetailResponseWithChildren(category);
    }

    @Override
    public List<CategorySummaryResponseDto> getSubcategories(Long parentId) {
        return categoryRepository.findByParentIdAndIsActiveTrueOrderByDisplayOrderAsc(parentId)
                .stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    private CategorySummaryResponseDto toSummaryResponse(Category category) {
        return CategorySummaryResponseDto.builder()
                .id(category.getId())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .cover(toAssetResponse(category.getCover()))
                .displayOrder(category.getDisplayOrder())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    private CategoryDetailResponseDto toDetailResponseWithChildren(Category category) {
        List<CategorySummaryResponseDto> children = null;
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            children = category.getChildren().stream()
                    .filter(Category::getIsActive)
                    .map(this::toSummaryResponse)
                    .toList();
        }

        return CategoryDetailResponseDto.builder()
                .id(category.getId())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .cover(toAssetResponse(category.getCover()))
                .displayOrder(category.getDisplayOrder())
                .isActive(category.getIsActive())
                .seo(toSeoResponse(category))
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .children(children)
                .build();
    }

    private SeoMetadataResponse toSeoResponse(Category category) {
        SeoMetadata seo = category.getSeo();
        if (seo == null) {
            return null;
        }
        return SeoMetadataResponse.builder()
                .metaTitle(seo.getMetaTitle())
                .metaDescription(seo.getMetaDescription())
                .metaKeywords(seo.getMetaKeywords())
                .canonicalUrl(seo.getCanonicalUrl())
                .ogTitle(seo.getOgTitle())
                .ogDescription(seo.getOgDescription())
                .ogImage(toAssetResponse(category.getOgImage()))
                .robots(seo.getRobots())
                .structuredData(seo.getStructuredData())
                .build();
    }

    private AssetResponse toAssetResponse(Asset asset) {
        if (asset == null) {
            return null;
        }
        return AssetResponse.builder()
                .id(asset.getId())
                .fileName(asset.getFileName())
                .originalName(asset.getOriginalName())
                .url(asset.getUrl())
                .contentType(asset.getContentType())
                .size(asset.getSize())
                .folder(asset.getFolder())
                .createdAt(asset.getCreatedAt())
                .build();
    }
}
