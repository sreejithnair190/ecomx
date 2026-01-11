package me.sreejithnair.ecomx_api.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;
import me.sreejithnair.ecomx_api.asset.entity.Asset;
import me.sreejithnair.ecomx_api.asset.repository.AssetRepository;
import me.sreejithnair.ecomx_api.asset.service.AssetService;
import me.sreejithnair.ecomx_api.catalog.dto.request.CategoryRequestDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategoryDetailResponseDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.CategorySummaryResponseDto;
import me.sreejithnair.ecomx_api.catalog.entity.Category;
import me.sreejithnair.ecomx_api.catalog.repository.CategoryRepository;
import me.sreejithnair.ecomx_api.catalog.service.AdminCategoryService;
import me.sreejithnair.ecomx_api.common.dto.SeoMetadataResponse;
import me.sreejithnair.ecomx_api.common.dto.ToggleActiveDto;
import me.sreejithnair.ecomx_api.common.embeddable.SeoMetadata;
import me.sreejithnair.ecomx_api.common.exception.ResourceAlreadyExistsException;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.common.util.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AssetFolder.CATEGORIES;
import static me.sreejithnair.ecomx_api.common.constant.AssetFolder.CATEGORIES_OG;
import static me.sreejithnair.ecomx_api.common.constant.CacheNameConstant.CATEGORIES_CACHE;
import static me.sreejithnair.ecomx_api.common.constant.SortableFields.CATEGORY;
import static me.sreejithnair.ecomx_api.common.util.Auth.getCurrentUser;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final AssetRepository assetRepository;
    private final AssetService assetService;
    private final ModelMapper modelMapper;

    @Override
    public Page<CategorySummaryResponseDto> getAllCategoriesPaginated(
            Integer page,
            Integer perPage,
            String sortBy,
            String sortDir,
            Integer isActive,
            Long parentId,
            String search
    ) {
        Pageable pageable = Helper.createPageable(page, perPage, sortBy, sortDir, CATEGORY);
        
        Boolean isActiveFilter = isActive == null ? null : isActive == 1;
        String searchFilter = (search == null || search.isBlank()) ? null : search;

        Page<Category> categories = categoryRepository.findAllWithFilters(isActiveFilter, searchFilter, parentId, pageable);
        return categories.map(this::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CATEGORIES_CACHE, key = "#id")
    public CategoryDetailResponseDto getCategoryById(Long id) {
        Category category = findById(id);
        return toDetailResponse(category);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CATEGORIES_CACHE, key = "#result.id")
    public CategoryDetailResponseDto createCategory(CategoryRequestDto request) {
        // Check if name already exists
        if (categoryRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Category with name '" + request.getName() + "' already exists");
        }
        
        Category category = new Category();
        
        // Generate slug from name
        String slug = generateSlug(request.getName());
        category.setSlug(slug);
        
        mapRequestToEntity(request, category);

        Category saved = categoryRepository.save(category);
        return toDetailResponse(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CATEGORIES_CACHE, key = "#id")
    public CategoryDetailResponseDto updateCategory(Long id, CategoryRequestDto request) {
        Category category = findById(id);
        
        // Check if name is being changed to an existing name
        if (!category.getName().equals(request.getName()) && 
            categoryRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new ResourceAlreadyExistsException("Category with name '" + request.getName() + "' already exists");
        }
        
        // Update slug if name changed
        if (!category.getName().equals(request.getName())) {
            category.setSlug(generateSlug(request.getName()));
        }
        
        mapRequestToEntity(request, category);

        Category updated = categoryRepository.save(category);
        return toDetailResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CATEGORIES_CACHE, key = "#id")
    public void deleteCategory(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CATEGORIES_CACHE, key = "#id")
    public void changeCategoryStatus(Long id, ToggleActiveDto toggleActiveDto) {
        Category category = findById(id);
        category.setIsActive(toggleActiveDto.getIsActive());
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CATEGORIES_CACHE, key = "'children:' + #parentId")
    public List<CategorySummaryResponseDto> getChildCategories(Long parentId) {
        findById(parentId); // Verify parent exists
        return categoryRepository.findByParentIdAndIsActiveTrueOrderByDisplayOrderAsc(parentId)
                .stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    private void mapRequestToEntity(CategoryRequestDto request, Category category) {
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setDisplayOrder(request.getDisplayOrder());
        category.setIsActive(request.getIsActive());

        // Parent
        if (request.getParentId() != null) {
            Category parent = findById(request.getParentId());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        // SEO metadata
        SeoMetadata seo = category.getSeo() != null ? category.getSeo() : new SeoMetadata();
        seo.setMetaTitle(request.getMetaTitle());
        seo.setMetaDescription(request.getMetaDescription());
        seo.setMetaKeywords(request.getMetaKeywords());
        seo.setCanonicalUrl(request.getCanonicalUrl());
        seo.setOgTitle(request.getOgTitle());
        seo.setOgDescription(request.getOgDescription());
        seo.setRobots(request.getRobots());
        seo.setStructuredData(request.getStructuredData());
        category.setSeo(seo);

        // Handle cover image - file upload takes precedence over ID
        if (request.getCoverFile() != null && !request.getCoverFile().isEmpty()) {
            // Delete old cover if exists
            if (category.getCover() != null) {
                assetService.delete(category.getCover().getId());
            }
            AssetResponse assetResponse = assetService.upload(request.getCoverFile(), CATEGORIES, getCurrentUser());
            Asset cover = modelMapper.map(assetResponse, Asset.class);
            category.setCover(cover);
        } else if (request.getCoverId() != null) {
            Asset cover = assetRepository.findById(request.getCoverId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + request.getCoverId()));
            category.setCover(cover);
        }

        // Handle OG image - file upload takes precedence over ID
        if (request.getOgImageFile() != null && !request.getOgImageFile().isEmpty()) {
            // Delete old OG image if exists
            if (category.getOgImage() != null) {
                assetService.delete(category.getOgImage().getId());
            }
            AssetResponse assetResponse = assetService.upload(request.getOgImageFile(), CATEGORIES_OG, getCurrentUser());
            Asset ogImage = modelMapper.map(assetResponse, Asset.class);
            category.setOgImage(ogImage);
        } else if (request.getOgImageId() != null) {
            Asset ogImage = assetRepository.findById(request.getOgImageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + request.getOgImageId()));
            category.setOgImage(ogImage);
        }
    }

    private Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    private String generateSlug(String name) {
        String baseSlug = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")  // remove special chars
                .replaceAll("\\s+", "-")           // spaces to hyphens
                .replaceAll("-+", "-")             // collapse multiple hyphens
                .replaceAll("^-|-$", "");          // trim leading/trailing hyphens

        String slug = baseSlug;
        int counter = 1;
        while (categoryRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter++;
        }
        return slug;
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

    private CategoryDetailResponseDto toDetailResponse(Category category) {
        List<CategorySummaryResponseDto> children = category.getChildren() != null
                ? category.getChildren().stream().map(this::toSummaryResponse).toList()
                : null;

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
