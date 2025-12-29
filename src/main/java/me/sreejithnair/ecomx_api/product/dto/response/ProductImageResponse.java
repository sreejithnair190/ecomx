package me.sreejithnair.ecomx_api.product.dto.response;

import lombok.Data;

@Data
public class ProductImageResponse {

    private Long id;
    private String imageUrl;
    private String altText;
    private Integer displayOrder;
    private Boolean isPrimary;
}
