package me.sreejithnair.ecomx_api.product.repository;

import me.sreejithnair.ecomx_api.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
