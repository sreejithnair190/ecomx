package me.sreejithnair.ecomx_api.catalog.repository;

import me.sreejithnair.ecomx_api.catalog.entity.Product;
import me.sreejithnair.ecomx_api.catalog.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);

    Optional<Product> findBySku(String sku);

    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    Page<Product> findByIsFeaturedTrue(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId AND p.status = :status")
    Page<Product> findByCategoryIdAndStatus(@Param("categoryId") Long categoryId, @Param("status") ProductStatus status, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId AND p.status = :status")
    Page<Product> findByBrandIdAndStatus(@Param("brandId") Long brandId, @Param("status") ProductStatus status, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND p.status = :status")
    Page<Product> searchByNameAndStatus(@Param("keyword") String keyword, @Param("status") ProductStatus status, Pageable pageable);

    List<Product> findTop10ByStatusOrderByViewCountDesc(ProductStatus status);

    boolean existsBySku(String sku);

    boolean existsBySlug(String slug);
}
