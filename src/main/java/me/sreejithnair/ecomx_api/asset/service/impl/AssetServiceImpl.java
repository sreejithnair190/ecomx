package me.sreejithnair.ecomx_api.asset.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sreejithnair.ecomx_api.asset.dto.AssetResponse;
import me.sreejithnair.ecomx_api.asset.entity.Asset;
import me.sreejithnair.ecomx_api.asset.repository.AssetRepository;
import me.sreejithnair.ecomx_api.asset.service.AssetService;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.user.core.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetServiceImpl implements AssetService {

    private final S3Client s3Client;
    private final AssetRepository assetRepository;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.cloudfront.domain}")
    private String cloudfrontDomain;

    @Override
    @Transactional
    public AssetResponse upload(MultipartFile file, String folder, User uploadedBy) {
        String fileName = generateFileName(file);
        String s3Key = folder + "/" + fileName;

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(s3Key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            String url = cloudfrontDomain + "/" + s3Key;

            Asset asset = Asset.builder()
                    .fileName(fileName)
                    .originalName(file.getOriginalFilename())
                    .s3Key(s3Key)
                    .url(url)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .folder(folder)
                    .uploadedBy(uploadedBy)
                    .build();

            asset = assetRepository.save(asset);
            log.info("Uploaded asset: {} to S3 key: {}", asset.getId(), s3Key);

            return mapToResponse(asset);
        } catch (IOException e) {
            log.error("Failed to upload file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    @Transactional
    public List<AssetResponse> uploadMultiple(List<MultipartFile> files, String folder, User uploadedBy) {
        return files.stream()
                .map(file -> upload(file, folder, uploadedBy))
                .toList();
    }

    @Override
    public AssetResponse getById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        return mapToResponse(asset);
    }

    @Override
    public List<AssetResponse> getByFolder(String folder) {
        return assetRepository.findByFolder(folder).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        deleteFromS3(asset.getS3Key());
        assetRepository.delete(asset);
        log.info("Deleted asset: {}", id);
    }

    @Override
    @Transactional
    public void deleteByS3Key(String s3Key) {
        Asset asset = assetRepository.findByS3Key(s3Key)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with key: " + s3Key));

        deleteFromS3(s3Key);
        assetRepository.delete(asset);
        log.info("Deleted asset with S3 key: {}", s3Key);
    }

    private void deleteFromS3(String s3Key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .build());
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename());
        return UUID.randomUUID().toString() + extension;
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    private AssetResponse mapToResponse(Asset asset) {
        return AssetResponse.builder()
                .id(asset.getId())
                .fileName(asset.getFileName())
                .originalName(asset.getOriginalName())
                .url(asset.getUrl())
                .contentType(asset.getContentType())
                .size(asset.getSize())
                .folder(asset.getFolder())
                .createdAt(asset.getCreatedAt())
                .build();
    }
}
