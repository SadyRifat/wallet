package com.digital.wallet.repository.user;

import com.digital.wallet.entity.user.Role;
import com.digital.wallet.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("select role from Role role where role.name=:name")
    Role getRoleByName(@Param("name") ERole name);
}
