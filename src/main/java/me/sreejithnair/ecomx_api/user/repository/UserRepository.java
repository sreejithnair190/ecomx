package me.sreejithnair.ecomx_api.user.repository;

import me.sreejithnair.ecomx_api.user.entity.User;
import me.sreejithnair.ecomx_api.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndStatusAndDeletedAtIsNull(String email, UserStatus status);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email AND u.status = 'ACTIVE' AND u.deletedAt IS NULL")
    Optional<User> findByEmailWithRoles(@Param("email") String email);

    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.name IN :roleNames AND u.status = :status AND u.deletedAt IS NULL")
    List<User> findActiveUsersByRoles(@Param("roleNames") List<String> roleNames, @Param("status") UserStatus status);

    Optional<User> findByIdAndDeletedAtIsNull(Long userId);
}
