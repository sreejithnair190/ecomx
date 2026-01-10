package me.sreejithnair.ecomx_api.catalog.service;

import me.sreejithnair.ecomx_api.catalog.dto.request.ProductRequest;
import me.sreejithnair.ecomx_api.catalog.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminProductService {

    Page<ProductResponseDto> getAllProducts(Pageable pageable);

    ProductResponseDto getProductById(Long id);

    ProductResponseDto createProduct(ProductRequest request);

    ProductResponseDto updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponseDto updateProductStatus(Long id, String status);
}
