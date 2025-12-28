package me.sreejithnair.ecomx_api.asset.service;

import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;
import me.sreejithnair.ecomx_api.user.core.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AssetService {

    AssetResponse upload(MultipartFile file, String folder, User uploadedBy);

    List<AssetResponse> uploadMultiple(List<MultipartFile> files, String folder, User uploadedBy);

    AssetResponse getById(Long id);

    List<AssetResponse> getByFolder(String folder);

    void delete(Long id);

    void deleteByS3Key(String s3Key);
}
