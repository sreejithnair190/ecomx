package me.sreejithnair.ecomx_api.catalog.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.catalog.dto.request.ProductRequest;
import me.sreejithnair.ecomx_api.catalog.dto.response.ProductResponseDto;
import me.sreejithnair.ecomx_api.catalog.service.AdminProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponseDto>>> getAllProducts(Pageable pageable) {
        Page<ProductResponseDto> products = adminProductService.getAllProducts(pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(@PathVariable Long id) {
        ProductResponseDto product = adminProductService.getProductById(id);
        return ApiResponse.ok(product);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponseDto product = adminProductService.createProduct(request);
        return ApiResponse.created(product, "Product created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        ProductResponseDto product = adminProductService.updateProduct(id, request);
        return ApiResponse.ok(product, "Product updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
        return ApiResponse.noContent();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProductStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        ProductResponseDto product = adminProductService.updateProductStatus(id, status);
        return ApiResponse.ok(product, "Product status updated!");
    }
}
