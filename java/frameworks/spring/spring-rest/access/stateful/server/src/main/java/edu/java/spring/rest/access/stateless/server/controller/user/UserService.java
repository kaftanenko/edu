package edu.java.spring.rest.access.stateless.server.controller.user;

import java.util.List;

public interface UserService {

	List<User> findAllUsers();

	User findById(long id);

	void saveUser(User user);

	void updateUser(User currentUser);

	void deleteUserById(long id);

	void deleteAllUsers();

	boolean isUserExist(User user);

}
