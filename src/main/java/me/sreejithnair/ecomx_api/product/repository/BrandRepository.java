package me.sreejithnair.ecomx_api.product.repository;

import me.sreejithnair.ecomx_api.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findBySlug(String slug);

    List<Brand> findByIsActiveTrueOrderByNameAsc();

    boolean existsBySlug(String slug);

    boolean existsByName(String name);
}
