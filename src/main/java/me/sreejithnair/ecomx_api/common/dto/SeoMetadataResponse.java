package me.sreejithnair.ecomx_api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeoMetadataResponse {

    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private String canonicalUrl;
    private String ogTitle;
    private String ogDescription;
    private AssetResponse ogImage;
    private String robots;
    private String structuredData;
}
