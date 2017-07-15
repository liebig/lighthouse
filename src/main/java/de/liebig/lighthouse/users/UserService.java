package de.liebig.lighthouse.users;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import de.liebig.lighthouse.roles.RoleService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Optional<User> findByName(String username) {
		return userRepository.findOneByNameIgnoreCase(username);
	}
	
	public boolean exists() {
		return userRepository.count() > 0;
	}
	
	
	public User createUser(String username, char[] password) {
		
		User newUser = new User();
		newUser.setName(username);
		newUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));
		newUser.setRoles(roleService.getDefaultRoles());
		newUser.setActive(true);
		
		return userRepository.save(newUser);
	}
	
}
