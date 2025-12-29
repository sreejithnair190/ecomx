package me.sreejithnair.ecomx_api.product.repository;

import me.sreejithnair.ecomx_api.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProductIdAndIsActiveTrue(Long productId);

    Optional<ProductVariant> findBySku(String sku);

    boolean existsBySku(String sku);
}
