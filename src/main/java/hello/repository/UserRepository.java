package hello.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import hello.model.*;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {

	
	public List<User> queryUserByNameAndEmail(String Name,String Email);
	public User queryUserById(Integer id);
	
}