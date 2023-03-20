package com.example.be.service.impl;

import com.example.be.model.user.Role;
import com.example.be.repository.user.IRoleRepository;
import com.example.be.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository iRoleRepository;

    @Override
    public Optional<Role> roleAdmin() {
        return iRoleRepository.roleAdmin();
    }

    @Override
    public Optional<Role> roleCustomer() {
        return iRoleRepository.roleCustomer();
    }

    @Override
    public Optional<Role> roleEmployee() {
        return iRoleRepository.roleEmployee();
    }

}