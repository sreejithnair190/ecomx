package me.sreejithnair.ecomx_api.product.repository;

import me.sreejithnair.ecomx_api.product.entity.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {

    List<AttributeValue> findByAttributeId(Long attributeId);
}
