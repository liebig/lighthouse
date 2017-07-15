package de.liebig.lighthouse.roles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Optional<Role> findByRole(String role) {
		String roleName = role;
		
		if (StringUtils.isEmpty(roleName)) {
			return Optional.empty();
		} else if(!StringUtils.startsWithIgnoreCase(roleName, "ROLE_")) {
			roleName = "ROLE_" + roleName;
		}
		
		return roleRepository.findOneByRoleIgnoreCase(roleName);
	}
	
	public List<Role> getDefaultRoles() {
		
		Optional<Role> userRole = this.findByRole("ROLE_USER");
		Optional<Role> adminRole = this.findByRole("ROLE_ADMIN");

		if (!userRole.isPresent() || !adminRole.isPresent()) {
			throw new NullPointerException("ROLE_USER or ROLE_ADMIN not found");
		}
		
		return Arrays.asList(userRole.get(), adminRole.get());
	}

}
