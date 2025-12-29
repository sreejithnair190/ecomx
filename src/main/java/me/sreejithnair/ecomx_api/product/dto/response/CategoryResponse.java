package me.sreejithnair.ecomx_api.product.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryResponse {

    private Long id;
    private Long parentId;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private Integer displayOrder;
    private Boolean isActive;
    private String metaTitle;
    private String metaDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategoryResponse> children;
}
