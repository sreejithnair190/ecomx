package me.sreejithnair.ecomx_api.catalog.dto.response;

import lombok.Data;
import me.sreejithnair.ecomx_api.catalog.enums.ProductStatus;
import me.sreejithnair.ecomx_api.catalog.enums.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductResponseDto {

    private Long id;
    private String sku;
    private String name;
    private String slug;
    private String description;
    private String shortDescription;
    private BrandResponseDto brand;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private ProductType productType;
    private ProductStatus status;
    private Boolean isFeatured;
    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategorySummaryResponseDto> categories;
    private List<ProductImageResponseDto> images;
}
