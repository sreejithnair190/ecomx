package me.sreejithnair.ecomx_api.user.address.repository;

import me.sreejithnair.ecomx_api.user.address.entity.Address;
import me.sreejithnair.ecomx_api.user.address.enums.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);

    List<Address> findByUserIdAndAddressType(Long userId, AddressType addressType);

    Optional<Address> findByIdAndUserId(Long id, Long userId);

    Optional<Address> findByUserIdAndIsDefaultTrue(Long userId);
}
