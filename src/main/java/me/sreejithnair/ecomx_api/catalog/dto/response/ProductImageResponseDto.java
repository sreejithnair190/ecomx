package me.sreejithnair.ecomx_api.catalog.dto.response;

import lombok.Data;

@Data
public class ProductImageResponseDto {

    private Long id;
    private String imageUrl;
    private String altText;
    private Integer displayOrder;
    private Boolean isPrimary;
}
