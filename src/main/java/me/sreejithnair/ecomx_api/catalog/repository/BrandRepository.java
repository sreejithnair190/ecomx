package me.sreejithnair.ecomx_api.catalog.repository;

import me.sreejithnair.ecomx_api.catalog.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findBySlug(String slug);

    List<Brand> findByIsActiveTrueOrderByNameAsc();

    boolean existsBySlug(String slug);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @EntityGraph(attributePaths = {"logo"})
    @Query("SELECT b FROM Brand b WHERE " +
            "(:isActive IS NULL OR b.isActive = :isActive) AND " +
            "(:search IS NULL OR b.name LIKE %:search%)")
    Page<Brand> findAllWithFilters(
            @Param("isActive") Boolean isActive,
            @Param("search") String search,
            Pageable pageable
    );
}
