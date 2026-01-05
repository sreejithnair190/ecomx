package me.sreejithnair.ecomx_api.product.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.common.dto.PagedResponse;
import me.sreejithnair.ecomx_api.common.dto.ToggleActiveDto;
import me.sreejithnair.ecomx_api.product.dto.request.BrandRequestDto;
import me.sreejithnair.ecomx_api.product.dto.response.BrandResponseDto;
import me.sreejithnair.ecomx_api.product.service.AdminBrandService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/admin/brands")
@RequiredArgsConstructor
public class AdminBrandController {

    private final AdminBrandService adminBrandService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<BrandResponseDto>>> getAllBrands(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Integer isActive,
            @RequestParam(required = false) String search
    ) {
        Page<BrandResponseDto> brands = adminBrandService.getAllBrandsPaginated(
                page,
                perPage,
                sortBy,
                sortDir,
                isActive,
                search
        );
        return ApiResponse.ok(PagedResponse.from(brands));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDto>> getBrandById(@PathVariable Long id) {
        BrandResponseDto brand = adminBrandService.getBrandById(id);
        return ApiResponse.ok(brand);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BrandResponseDto>> createBrand(@Valid @ModelAttribute BrandRequestDto brandRequestDto) {
        BrandResponseDto brand = adminBrandService.createBrand(brandRequestDto);
        return ApiResponse.created(brand, "Brand created successfully!");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BrandResponseDto>> updateBrand(
            @PathVariable Long id,
            @Valid @ModelAttribute BrandRequestDto brandRequestDto
    ) {
        BrandResponseDto brand = adminBrandService.updateBrand(id, brandRequestDto);
        return ApiResponse.ok(brand, "Brand updated successfully!");
    }


    @PatchMapping("/change-status/{id}")
    public ResponseEntity<ApiResponse<String>> changeBrandStatus(
            @PathVariable Long id,
            ToggleActiveDto toggleActiveDto
    ) {
        adminBrandService.changeBrandStatus(id, toggleActiveDto);
        return ApiResponse.ok("Brand status changed successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBrand(@PathVariable Long id) {
        adminBrandService.deleteBrand(id);
        return ApiResponse.ok("Brand deleted successfully");
    }
}
