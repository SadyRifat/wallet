package com.digital.wallet.repository.user;

import com.digital.wallet.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.digital.wallet.utls.StaticTextConfig.DB_SCHEMA;

@Repository
public interface UserCredentialRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.email=:email")
    Optional<User> findByUserEmail(@Param("email") String email);

    @Query(value = "SELECT name FROM " + DB_SCHEMA + ".users WHERE email = :email", nativeQuery = true)
    String getNameFromEmail(@Param("email") String email);
}
