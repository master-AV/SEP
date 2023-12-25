package ftn.sep.webshop.service.implementation;


import ftn.sep.db.Role;
import ftn.sep.webshop.repository.RoleRepository;
import ftn.sep.webshop.service.interfaces.IRoleService;
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
