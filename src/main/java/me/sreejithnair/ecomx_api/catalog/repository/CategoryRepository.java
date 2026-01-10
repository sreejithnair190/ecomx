package me.sreejithnair.ecomx_api.catalog.repository;

import me.sreejithnair.ecomx_api.catalog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);

    List<Category> findByParentIsNullAndIsActiveTrueOrderByDisplayOrderAsc();

    List<Category> findByParentIdAndIsActiveTrueOrderByDisplayOrderAsc(Long parentId);

    List<Category> findByIsActiveTrueOrderByDisplayOrderAsc();

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL AND c.isActive = true ORDER BY c.displayOrder")
    List<Category> findRootCategoriesWithChildren();

    boolean existsBySlug(String slug);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @EntityGraph(attributePaths = {"cover", "ogImage"})
    @Query("SELECT c FROM Category c WHERE " +
            "(:isActive IS NULL OR c.isActive = :isActive) AND " +
            "(:search IS NULL OR c.name LIKE %:search%) AND " +
            "(:parentId IS NULL OR c.parent.id = :parentId)")
    Page<Category> findAllWithFilters(
            @Param("isActive") Boolean isActive,
            @Param("search") String search,
            @Param("parentId") Long parentId,
            Pageable pageable
    );
}
