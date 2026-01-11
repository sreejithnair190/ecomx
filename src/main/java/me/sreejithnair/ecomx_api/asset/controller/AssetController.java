package me.sreejithnair.ecomx_api.asset.controller;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;
import me.sreejithnair.ecomx_api.asset.service.AssetService;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.user.core.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/admin/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AssetResponse>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder,
            @AuthenticationPrincipal User user) {
        AssetResponse response = assetService.upload(file, folder, user);
        return ApiResponse.created(response, "Asset uploaded successfully");
    }

    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<AssetResponse>>> uploadMultiple(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("folder") String folder,
            @AuthenticationPrincipal User user) {
        List<AssetResponse> responses = assetService.uploadMultiple(files, folder, user);
        return ApiResponse.created(responses, "Assets uploaded successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetResponse>> getById(@PathVariable Long id) {
        return ApiResponse.ok(assetService.getById(id));
    }

    @GetMapping("/folder/{folder}")
    public ResponseEntity<ApiResponse<List<AssetResponse>>> getByFolder(@PathVariable String folder) {
        return ApiResponse.ok(assetService.getByFolder(folder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        assetService.delete(id);
        return ApiResponse.noContent();
    }
}
