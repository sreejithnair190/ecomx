package me.sreejithnair.ecomx_api.product.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;
import me.sreejithnair.ecomx_api.asset.entity.Asset;
import me.sreejithnair.ecomx_api.asset.service.AssetService;
import me.sreejithnair.ecomx_api.common.exception.ResourceAlreadyExistsException;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.product.dto.request.BrandRequestDto;
import me.sreejithnair.ecomx_api.product.dto.response.BrandResponseDto;
import me.sreejithnair.ecomx_api.product.entity.Brand;
import me.sreejithnair.ecomx_api.product.repository.BrandRepository;
import me.sreejithnair.ecomx_api.product.service.AdminBrandService;
import me.sreejithnair.ecomx_api.user.core.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.util.Helper.getCurrentUser;

@Service
@RequiredArgsConstructor
public class AdminBrandServiceImpl implements AdminBrandService {

    private final BrandRepository brandRepository;
    private final AssetService assetService;
    private final ModelMapper modelMapper;

    @Override
    public List<BrandResponseDto> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
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

        User user = getCurrentUser();
        Brand brandToCreate = modelMapper.map(brandRequestDto, Brand.class);

        AssetResponse assetResponse = assetService.upload(brandRequestDto.getLogoFile(), "/brand", user);
        Asset asset = modelMapper.map(assetResponse, Asset.class);
        brandToCreate.setLogo(asset);

        String slug = this.generateSlug(brandToCreate.getName());
        brandToCreate.setSlug(slug);

        Brand savedBrand = brandRepository.save(brandToCreate);
        return toResponse(savedBrand);
    }

    @Override
    @Transactional
    public BrandResponseDto updateBrand(Long id, BrandRequestDto request) {
        Brand brand = findById(id);

        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        brand.setIsActive(request.getIsActive());

        Brand updated = brandRepository.save(brand);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteBrand(Long id) {
        Brand brand = findById(id);
        brandRepository.delete(brand);
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
        BrandResponseDto dto = modelMapper.map(brand, BrandResponseDto.class);
        if (brand.getLogo() != null) {
            dto.setAssetId(brand.getLogo().getId());
        }
        return dto;
    }
}
