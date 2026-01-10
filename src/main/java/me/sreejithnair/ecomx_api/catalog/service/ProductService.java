package me.sreejithnair.ecomx_api.catalog.service;

import me.sreejithnair.ecomx_api.catalog.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ProductResponseDto> getActiveProducts(Pageable pageable);

    ProductResponseDto getProductBySlug(String slug);

    Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable);

    Page<ProductResponseDto> getProductsByBrand(Long brandId, Pageable pageable);

    Page<ProductResponseDto> searchProducts(String keyword, Pageable pageable);

    Page<ProductResponseDto> getFeaturedProducts(Pageable pageable);

    List<ProductResponseDto> getPopularProducts();
}
