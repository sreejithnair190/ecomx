package me.sreejithnair.ecomx_api.product.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.product.dto.request.BrandRequestDto;
import me.sreejithnair.ecomx_api.product.dto.response.BrandResponseDto;
import me.sreejithnair.ecomx_api.product.service.AdminBrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/admin/brands")
@RequiredArgsConstructor
public class AdminBrandController {

    private final AdminBrandService adminBrandService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponseDto>>> getAllBrands() {
        List<BrandResponseDto> brands = adminBrandService.getAllBrands();
        return ApiResponse.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDto>> getBrandById(@PathVariable Long id) {
        BrandResponseDto brand = adminBrandService.getBrandById(id);
        return ApiResponse.ok(brand);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<BrandResponseDto>> createBrand(@Valid @ModelAttribute BrandRequestDto brandRequestDto) {
        BrandResponseDto brand = adminBrandService.createBrand(brandRequestDto);
        return ApiResponse.created(brand, "Brand created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDto>> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandRequestDto brandRequestDto
    ) {
        BrandResponseDto brand = adminBrandService.updateBrand(id, brandRequestDto);
        return ApiResponse.ok(brand, "Brand updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Long id) {
        adminBrandService.deleteBrand(id);
        return ApiResponse.noContent();
    }
}
