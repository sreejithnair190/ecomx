package me.sreejithnair.ecomx_api.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.sreejithnair.ecomx_api.catalog.enums.ProductType;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequest {

    @NotBlank
    @Size(max = 100)
    private String sku;

    @NotBlank
    @Size(max = 300)
    private String name;

    @NotBlank
    @Size(max = 300)
    private String slug;

    private String description;

    @Size(max = 500)
    private String shortDescription;

    private Long brandId;

    @NotNull
    @Positive
    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private BigDecimal costPrice;

    private ProductType productType = ProductType.SIMPLE;

    private Boolean isFeatured = false;

    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;

    @Size(max = 200)
    private String metaTitle;

    private String metaDescription;

    private String metaKeywords;

    private List<Long> categoryIds;
}
