package me.sreejithnair.ecomx_api.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    private Long parentId;

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String slug;

    private String description;

    @Size(max = 500)
    private String imageUrl;

    private Integer displayOrder = 0;

    private Boolean isActive = true;

    @Size(max = 200)
    private String metaTitle;

    private String metaDescription;
}
