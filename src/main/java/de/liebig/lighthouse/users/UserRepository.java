package de.liebig.lighthouse.users;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findOneByNameIgnoreCase(String name);
	
	long count();

}
