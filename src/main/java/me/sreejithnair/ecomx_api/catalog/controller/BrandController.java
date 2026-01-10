package me.sreejithnair.ecomx_api.catalog.controller;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.catalog.dto.response.BrandResponseDto;
import me.sreejithnair.ecomx_api.catalog.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponseDto>>> getActiveBrands() {
        List<BrandResponseDto> brands = brandService.getActiveBrands();
        return ApiResponse.ok(brands);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<BrandResponseDto>> getBrandBySlug(@PathVariable String slug) {
        BrandResponseDto brand = brandService.getBrandBySlug(slug);
        return ApiResponse.ok(brand);
    }
}
