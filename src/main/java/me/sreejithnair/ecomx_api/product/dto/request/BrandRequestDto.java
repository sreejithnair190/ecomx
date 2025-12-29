package me.sreejithnair.ecomx_api.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.sreejithnair.ecomx_api.common.validation.ValidImage;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BrandRequestDto {

    @NotBlank(message = "Brand Name is required")
    @Size(max = 200, message = "Brand name must not exceed 200 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private Long assetId;

    @ValidImage(maxSizeInMB = 2, allowedTypes = {"image/jpeg", "image/png", "image/webp"})
    private MultipartFile logoFile;

    private Boolean isActive = true;
}
