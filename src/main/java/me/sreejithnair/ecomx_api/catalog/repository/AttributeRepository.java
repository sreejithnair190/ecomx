package me.sreejithnair.ecomx_api.catalog.repository;

import me.sreejithnair.ecomx_api.catalog.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Optional<Attribute> findByName(String name);

    boolean existsByName(String name);
}
