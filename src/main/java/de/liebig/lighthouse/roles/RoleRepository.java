package de.liebig.lighthouse.roles;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Optional<Role> findOneByRoleIgnoreCase(String role);
	
}
