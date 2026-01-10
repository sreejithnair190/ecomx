package me.sreejithnair.ecomx_api.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.sreejithnair.ecomx_api.common.validation.ValidImage;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    private Long parentId;

    @NotBlank(message = "Category name is required")
    @Size(max = 200, message = "Category name must not exceed 200 characters")
    private String name;

    private String description;

    @ValidImage(maxSizeInMB = 5, allowedTypes = {"image/jpeg", "image/png", "image/webp"})
    private MultipartFile coverFile;

    @ValidImage(maxSizeInMB = 5, allowedTypes = {"image/jpeg", "image/png", "image/webp"})
    private MultipartFile ogImageFile;

    private Long coverId;

    private Long ogImageId;

    private Integer displayOrder = 0;

    private Boolean isActive = true;

    // SEO fields
    @Size(max = 200, message = "Meta title must not exceed 200 characters")
    private String metaTitle;

    @Size(max = 500, message = "Meta description must not exceed 500 characters")
    private String metaDescription;

    @Size(max = 500, message = "Meta keywords must not exceed 500 characters")
    private String metaKeywords;

    @Size(max = 500, message = "Canonical URL must not exceed 500 characters")
    private String canonicalUrl;

    @Size(max = 200, message = "OG title must not exceed 200 characters")
    private String ogTitle;

    @Size(max = 500, message = "OG description must not exceed 500 characters")
    private String ogDescription;

    @Size(max = 100, message = "Robots directive must not exceed 100 characters")
    private String robots;

    private String structuredData;
}
