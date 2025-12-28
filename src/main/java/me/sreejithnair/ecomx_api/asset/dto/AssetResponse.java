package me.sreejithnair.ecomx_api.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponse {
    private Long id;
    private String fileName;
    private String originalName;
    private String url;
    private String contentType;
    private Long size;
    private String folder;
    private LocalDateTime createdAt;
}
