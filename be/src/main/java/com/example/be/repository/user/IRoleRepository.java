package com.example.be.repository.user;

import com.example.be.model.user.Role;
import com.example.be.model.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleName name);

    @Query(value = "select r.* from role r where r.name = 'ROLE_ADMIN'", nativeQuery = true)
    Optional<Role> roleAdmin();

    @Query(value = "select r.* from role r where r.name = 'ROLE_CUSTOMER'", nativeQuery = true)
    Optional<Role> roleCustomer();

    @Query(value = "select r.* from role r where r.name = 'ROLE_EMPLOYEE'", nativeQuery = true)
    Optional<Role> roleEmployee();
}
