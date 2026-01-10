package me.sreejithnair.ecomx_api.catalog.service;

import me.sreejithnair.ecomx_api.catalog.dto.response.BrandResponseDto;

import java.util.List;

public interface BrandService {

    List<BrandResponseDto> getActiveBrands();

    BrandResponseDto getBrandBySlug(String slug);
}
