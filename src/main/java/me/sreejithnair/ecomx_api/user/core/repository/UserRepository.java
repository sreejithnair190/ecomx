package me.sreejithnair.ecomx_api.user.core.repository;

import me.sreejithnair.ecomx_api.user.core.entity.User;
import me.sreejithnair.ecomx_api.user.core.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(String email);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    boolean existsByEmail(String email);

    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.name IN :roleNames AND u.status = :status AND u.deletedAt IS NULL")
    List<User> findActiveUsersByRoles(@Param("roleNames") List<String> roleNames, @Param("status") UserStatus status);
}
