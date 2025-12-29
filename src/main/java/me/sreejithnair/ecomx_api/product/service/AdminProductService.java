package me.sreejithnair.ecomx_api.product.service;

import me.sreejithnair.ecomx_api.product.dto.request.ProductRequest;
import me.sreejithnair.ecomx_api.product.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminProductService {

    Page<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse getProductById(Long id);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse updateProductStatus(Long id, String status);
}
