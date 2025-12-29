package me.sreejithnair.ecomx_api.product.service;

import me.sreejithnair.ecomx_api.product.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ProductResponse> getActiveProducts(Pageable pageable);

    ProductResponse getProductBySlug(String slug);

    Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable);

    Page<ProductResponse> getProductsByBrand(Long brandId, Pageable pageable);

    Page<ProductResponse> searchProducts(String keyword, Pageable pageable);

    Page<ProductResponse> getFeaturedProducts(Pageable pageable);

    List<ProductResponse> getPopularProducts();
}
