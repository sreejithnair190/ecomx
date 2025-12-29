package me.sreejithnair.ecomx_api.product.controller;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.product.dto.response.ProductResponse;
import me.sreejithnair.ecomx_api.product.service.ProductService;
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
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProducts(Pageable pageable) {
        Page<ProductResponse> products = productService.getActiveProducts(pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductBySlug(@PathVariable String slug) {
        ProductResponse product = productService.getProductBySlug(slug);
        return ApiResponse.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProductsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable
    ) {
        Page<ProductResponse> products = productService.getProductsByCategory(categoryId, pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProductsByBrand(
            @PathVariable Long brandId,
            Pageable pageable
    ) {
        Page<ProductResponse> products = productService.getProductsByBrand(brandId, pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> searchProducts(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        Page<ProductResponse> products = productService.searchProducts(keyword, pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getFeaturedProducts(Pageable pageable) {
        Page<ProductResponse> products = productService.getFeaturedProducts(pageable);
        return ApiResponse.ok(products);
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getPopularProducts() {
        List<ProductResponse> products = productService.getPopularProducts();
        return ApiResponse.ok(products);
    }
}
