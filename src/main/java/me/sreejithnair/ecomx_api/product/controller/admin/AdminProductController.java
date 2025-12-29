package me.sreejithnair.ecomx_api.product.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.product.dto.request.ProductRequest;
import me.sreejithnair.ecomx_api.product.dto.response.ProductResponse;
import me.sreejithnair.ecomx_api.product.service.AdminProductService;
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
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllProducts(Pageable pageable) {
        Page<ProductResponse> products = adminProductService.getAllProducts(pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        ProductResponse product = adminProductService.getProductById(id);
        return ApiResponse.ok(product);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse product = adminProductService.createProduct(request);
        return ApiResponse.created(product, "Product created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        ProductResponse product = adminProductService.updateProduct(id, request);
        return ApiResponse.ok(product, "Product updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
        return ApiResponse.noContent();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        ProductResponse product = adminProductService.updateProductStatus(id, status);
        return ApiResponse.ok(product, "Product status updated!");
    }
}
