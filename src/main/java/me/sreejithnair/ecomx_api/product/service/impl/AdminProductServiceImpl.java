package me.sreejithnair.ecomx_api.product.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.product.dto.request.ProductRequest;
import me.sreejithnair.ecomx_api.product.dto.response.ProductResponse;
import me.sreejithnair.ecomx_api.product.entity.Brand;
import me.sreejithnair.ecomx_api.product.entity.Category;
import me.sreejithnair.ecomx_api.product.entity.Product;
import me.sreejithnair.ecomx_api.product.enums.ProductStatus;
import me.sreejithnair.ecomx_api.product.repository.BrandRepository;
import me.sreejithnair.ecomx_api.product.repository.CategoryRepository;
import me.sreejithnair.ecomx_api.product.repository.ProductRepository;
import me.sreejithnair.ecomx_api.product.service.AdminProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = findById(id);
        return toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = modelMapper.map(request, Product.class);

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + request.getBrandId()));
            product.setBrand(brand);
        }

        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
            product.setCategories(categories);
        }

        product.setStatus(ProductStatus.DRAFT);
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = findById(id);

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setShortDescription(request.getShortDescription());
        product.setBasePrice(request.getBasePrice());
        product.setSalePrice(request.getSalePrice());
        product.setCostPrice(request.getCostPrice());
        product.setProductType(request.getProductType());
        product.setIsFeatured(request.getIsFeatured());
        product.setWeight(request.getWeight());
        product.setLength(request.getLength());
        product.setWidth(request.getWidth());
        product.setHeight(request.getHeight());
        product.setMetaTitle(request.getMetaTitle());
        product.setMetaDescription(request.getMetaDescription());
        product.setMetaKeywords(request.getMetaKeywords());

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + request.getBrandId()));
            product.setBrand(brand);
        } else {
            product.setBrand(null);
        }

        if (request.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
            product.getCategories().clear();
            product.getCategories().addAll(categories);
        }

        Product updated = productRepository.save(product);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProductStatus(Long id, String status) {
        Product product = findById(id);
        product.setStatus(ProductStatus.valueOf(status.toUpperCase()));
        Product updated = productRepository.save(product);
        return toResponse(updated);
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    private ProductResponse toResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }
}
