package me.sreejithnair.ecomx_api.common.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeoMetadata {

    @Column(name = "meta_title", length = 200)
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    private String metaDescription;

    @Column(name = "meta_keywords", length = 500)
    private String metaKeywords;

    @Column(name = "canonical_url", length = 500)
    private String canonicalUrl;

    @Column(name = "og_title", length = 200)
    private String ogTitle;

    @Column(name = "og_description", length = 500)
    private String ogDescription;

    @Column(name = "robots", length = 100)
    private String robots;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "structured_data", columnDefinition = "JSONB")
    private String structuredData;
}
