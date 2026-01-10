package me.sreejithnair.ecomx_api.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;
import me.sreejithnair.ecomx_api.common.dto.SeoMetadataResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailResponseDto {

    private Long id;
    private Long parentId;
    private String name;
    private String slug;
    private String description;
    private AssetResponse cover;
    private Integer displayOrder;
    private Boolean isActive;
    private SeoMetadataResponse seo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategorySummaryResponseDto> children;
}
