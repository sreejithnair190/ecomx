package me.sreejithnair.ecomx_api.catalog.controller;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.catalog.dto.response.ProductResponseDto;
import me.sreejithnair.ecomx_api.catalog.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponseDto>>> getProducts(Pageable pageable) {
        Page<ProductResponseDto> products = productService.getActiveProducts(pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductBySlug(@PathVariable String slug) {
        ProductResponseDto product = productService.getProductBySlug(slug);
        return ApiResponse.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<ProductResponseDto>>> getProductsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable
    ) {
        Page<ProductResponseDto> products = productService.getProductsByCategory(categoryId, pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<ApiResponse<Page<ProductResponseDto>>> getProductsByBrand(
            @PathVariable Long brandId,
            Pageable pageable
    ) {
        Page<ProductResponseDto> products = productService.getProductsByBrand(brandId, pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponseDto>>> searchProducts(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        Page<ProductResponseDto> products = productService.searchProducts(keyword, pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<Page<ProductResponseDto>>> getFeaturedProducts(Pageable pageable) {
        Page<ProductResponseDto> products = productService.getFeaturedProducts(pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getPopularProducts() {
        List<ProductResponseDto> products = productService.getPopularProducts();
        return ApiResponse.ok(products);
    }
}
