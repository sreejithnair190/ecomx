package me.sreejithnair.ecomx_api.product.service;

import me.sreejithnair.ecomx_api.common.dto.ToggleActiveDto;
import me.sreejithnair.ecomx_api.product.dto.request.BrandRequestDto;
import me.sreejithnair.ecomx_api.product.dto.response.BrandResponseDto;
import org.springframework.data.domain.Page;


public interface AdminBrandService {

    Page<BrandResponseDto> getAllBrandsPaginated(
            Integer page,
            Integer perPage,
            String sortBy,
            String sortDir,
            Integer isActive,
            String search
    );

    BrandResponseDto getBrandById(Long id);

    BrandResponseDto createBrand(BrandRequestDto brandRequestDto);

    BrandResponseDto updateBrand(Long id, BrandRequestDto brandRequestDto);

    void deleteBrand(Long id);

    void changeBrandStatus(Long id, ToggleActiveDto toggleActiveDto);
}
