package me.sreejithnair.ecomx_api.catalog.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandResponseDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private Long assetId;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
