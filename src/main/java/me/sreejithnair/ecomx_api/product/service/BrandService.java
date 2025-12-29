package me.sreejithnair.ecomx_api.product.service;

import me.sreejithnair.ecomx_api.product.dto.response.BrandResponseDto;

import java.util.List;

public interface BrandService {

    List<BrandResponseDto> getActiveBrands();

    BrandResponseDto getBrandBySlug(String slug);
}
