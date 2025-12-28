package me.sreejithnair.ecomx_api.asset.repository;

import me.sreejithnair.ecomx_api.asset.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByS3Key(String s3Key);

    List<Asset> findByFolder(String folder);

    List<Asset> findByUploadedById(Long userId);
}
