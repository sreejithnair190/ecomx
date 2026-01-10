package me.sreejithnair.ecomx_api.common.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SeoMetadataRequest {

    @Size(max = 200)
    private String metaTitle;

    @Size(max = 500)
    private String metaDescription;

    @Size(max = 500)
    private String metaKeywords;

    @Size(max = 500)
    private String canonicalUrl;

    @Size(max = 200)
    private String ogTitle;

    @Size(max = 500)
    private String ogDescription;

    @Size(max = 100)
    private String robots;

    private String structuredData;
}
