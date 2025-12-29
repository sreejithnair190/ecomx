package me.sreejithnair.ecomx_api.product.service;

import me.sreejithnair.ecomx_api.product.dto.request.BrandRequestDto;
import me.sreejithnair.ecomx_api.product.dto.response.BrandResponseDto;

import java.util.List;

public interface AdminBrandService {

    List<BrandResponseDto> getAllBrands();

    BrandResponseDto getBrandById(Long id);

    BrandResponseDto createBrand(BrandRequestDto brandRequestDto);

    BrandResponseDto updateBrand(Long id, BrandRequestDto brandRequestDto);

    void deleteBrand(Long id);
}
