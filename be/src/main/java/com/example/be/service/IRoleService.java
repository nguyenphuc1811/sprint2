package com.example.be.service;

import com.example.be.model.user.Role;

import java.util.Optional;

public interface IRoleService {

    Optional<Role> roleAdmin();

    Optional<Role> roleCustomer();

    Optional<Role> roleEmployee();
}