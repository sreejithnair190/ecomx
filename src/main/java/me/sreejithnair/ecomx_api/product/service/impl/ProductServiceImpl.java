package me.sreejithnair.ecomx_api.product.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.product.dto.response.ProductResponse;
import me.sreejithnair.ecomx_api.product.entity.Product;
import me.sreejithnair.ecomx_api.product.enums.ProductStatus;
import me.sreejithnair.ecomx_api.product.repository.ProductRepository;
import me.sreejithnair.ecomx_api.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<ProductResponse> getActiveProducts(Pageable pageable) {
        return productRepository.findByStatus(ProductStatus.ACTIVE, pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with slug: " + slug));

        product.setViewCount(product.getViewCount() + 1);
        productRepository.save(product);

        return toResponse(product);
    }

    @Override
    public Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndStatus(categoryId, ProductStatus.ACTIVE, pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<ProductResponse> getProductsByBrand(Long brandId, Pageable pageable) {
        return productRepository.findByBrandIdAndStatus(brandId, ProductStatus.ACTIVE, pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<ProductResponse> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchByNameAndStatus(keyword, ProductStatus.ACTIVE, pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<ProductResponse> getFeaturedProducts(Pageable pageable) {
        return productRepository.findByIsFeaturedTrue(pageable)
                .map(this::toResponse);
    }

    @Override
    public List<ProductResponse> getPopularProducts() {
        return productRepository.findTop10ByStatusOrderByViewCountDesc(ProductStatus.ACTIVE)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponse toResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }
}
