package me.sreejithnair.ecomx_api.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryResponseDto {

    private Long id;
    private Long parentId;
    private String name;
    private String slug;
    private String description;
    private AssetResponse cover;
    private Integer displayOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
