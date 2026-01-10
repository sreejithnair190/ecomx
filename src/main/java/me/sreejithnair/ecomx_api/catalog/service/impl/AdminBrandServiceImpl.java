package me.sreejithnair.ecomx_api.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.asset.entity.Asset;
import me.sreejithnair.ecomx_api.asset.repository.AssetRepository;
import me.sreejithnair.ecomx_api.asset.service.AssetService;
import me.sreejithnair.ecomx_api.common.dto.ToggleActiveDto;
import me.sreejithnair.ecomx_api.common.exception.ResourceAlreadyExistsException;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.common.util.Helper;
import me.sreejithnair.ecomx_api.catalog.dto.request.BrandRequestDto;
import me.sreejithnair.ecomx_api.catalog.dto.response.BrandResponseDto;
import me.sreejithnair.ecomx_api.catalog.entity.Brand;
import me.sreejithnair.ecomx_api.catalog.repository.BrandRepository;
import me.sreejithnair.ecomx_api.catalog.service.AdminBrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static me.sreejithnair.ecomx_api.common.constant.SortableFields.BRAND;

@Service
@RequiredArgsConstructor
public class AdminBrandServiceImpl implements AdminBrandService {

    private final BrandRepository brandRepository;
    private final AssetRepository assetRepository;
    private final AssetService assetService;


    @Override
    public Page<BrandResponseDto> getAllBrandsPaginated(Integer page, Integer perPage, String sortBy, String sortDir, Integer isActive, String search) {
        Pageable pageable = Helper.createPageable(page, perPage, sortBy, sortDir, BRAND);

        Boolean isActiveFilter = isActive == null ? null : isActive == 1;
        String searchFilter = (search == null || search.isBlank()) ? null : search;

        Page<Brand> brands = brandRepository.findAllWithFilters(isActiveFilter, searchFilter, pageable);
        return brands.map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponseDto getBrandById(Long id) {
        Brand brand = findById(id);
        return toResponse(brand);
    }

    @Override
    @Transactional
    public BrandResponseDto createBrand(BrandRequestDto brandRequestDto) {
        if (brandRepository.existsByName(brandRequestDto.getName())) {
            throw new ResourceAlreadyExistsException("Brand with name '" + brandRequestDto.getName() + "' already exists");
        }

        Brand brandToCreate = new Brand();
        brandToCreate.setName(brandRequestDto.getName());
        brandToCreate.setDescription(brandRequestDto.getDescription());
        brandToCreate.setIsActive(brandRequestDto.getIsActive());
        brandToCreate.setSlug(generateSlug(brandRequestDto.getName()));

        // Set logo if provided
        if (brandRequestDto.getLogoId() != null) {
            Asset logo = assetRepository.findById(brandRequestDto.getLogoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + brandRequestDto.getLogoId()));
            brandToCreate.setLogo(logo);
        }

        Brand savedBrand = brandRepository.save(brandToCreate);
        return toResponse(savedBrand);
    }

    @Override
    @Transactional
    public BrandResponseDto updateBrand(Long id, BrandRequestDto brandRequestDto) {
        Brand brand = findById(id);

        // Check if name is being changed to an existing name
        if (!brand.getName().equals(brandRequestDto.getName()) &&
            brandRepository.existsByNameAndIdNot(brandRequestDto.getName(), id)) {
            throw new ResourceAlreadyExistsException("Brand with name '" + brandRequestDto.getName() + "' already exists");
        }

        // Update slug if name changed
        if (!brand.getName().equals(brandRequestDto.getName())) {
            brand.setSlug(generateSlug(brandRequestDto.getName()));
        }

        brand.setName(brandRequestDto.getName());
        brand.setDescription(brandRequestDto.getDescription());
        brand.setIsActive(brandRequestDto.getIsActive());

        // Update logo if provided
        if (brandRequestDto.getLogoId() != null) {
            // Delete old logo if exists and different
            if (brand.getLogo() != null && !brand.getLogo().getId().equals(brandRequestDto.getLogoId())) {
                assetService.delete(brand.getLogo().getId());
            }
            Asset logo = assetRepository.findById(brandRequestDto.getLogoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + brandRequestDto.getLogoId()));
            brand.setLogo(logo);
        }

        Brand updated = brandRepository.save(brand);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteBrand(Long id) {
        Brand brand = findById(id);
        if (brand.getLogo() != null) {
            assetService.delete(brand.getLogo().getId());
        }
        brandRepository.delete(brand);
    }

    @Override
    public void changeBrandStatus(Long id, ToggleActiveDto toggleActiveDto) {
        Brand brand = findById(id);
        brand.setIsActive(toggleActiveDto.getIsActive());
        brandRepository.save(brand);
    }

    private Brand findById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
    }

    private String generateSlug(String name) {
        String baseSlug = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")  // remove special chars
                .replaceAll("\\s+", "-")           // spaces to hyphens
                .replaceAll("-+", "-")             // collapse multiple hyphens
                .replaceAll("^-|-$", "");          // trim leading/trailing hyphens

        String slug = baseSlug;
        int counter = 1;
        while (brandRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter++;
        }
        return slug;
    }

    private BrandResponseDto toResponse(Brand brand) {
        BrandResponseDto dto = new BrandResponseDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setSlug(brand.getSlug());
        dto.setDescription(brand.getDescription());
        dto.setIsActive(brand.getIsActive());
        dto.setCreatedAt(brand.getCreatedAt());
        dto.setUpdatedAt(brand.getUpdatedAt());
        if (brand.getLogo() != null) {
            dto.setAssetId(brand.getLogo().getId());
            dto.setLogoUrl(brand.getLogo().getUrl());
        }
        return dto;
    }

}
