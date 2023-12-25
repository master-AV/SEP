package ftn.sep.webshop.repository;

import ftn.sep.db.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(String name);
}
