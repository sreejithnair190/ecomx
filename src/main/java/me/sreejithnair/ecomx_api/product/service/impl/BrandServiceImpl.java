package me.sreejithnair.ecomx_api.product.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.product.dto.response.BrandResponseDto;
import me.sreejithnair.ecomx_api.product.entity.Brand;
import me.sreejithnair.ecomx_api.product.repository.BrandRepository;
import me.sreejithnair.ecomx_api.product.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<BrandResponseDto> getActiveBrands() {
        return brandRepository.findByIsActiveTrueOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BrandResponseDto getBrandBySlug(String slug) {
        Brand brand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with slug: " + slug));
        return toResponse(brand);
    }

    private BrandResponseDto toResponse(Brand brand) {
        return modelMapper.map(brand, BrandResponseDto.class);
    }
}
