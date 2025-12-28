package me.sreejithnair.ecomx_api.asset.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetUploadRequest {
    @NotBlank(message = "Folder is required")
    private String folder;
}
