package com.sep.id.service.implementation;


import com.sep.id.repository.RoleRepository;
import com.sep.id.service.interfaces.IRoleService;
import ftn.sep.db.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findRoleByRoleName(name);
    }
}
