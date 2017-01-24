package edu.java.spring.business.greeting.service;

import java.util.List;

import edu.java.spring.business.greeting.domain.Greeting;

public interface GreetingService {

	// ... finder methods

	List<Greeting> findAll();

	Greeting findById(long id);

	Greeting findByName(String name);

	boolean isExist(Greeting greeting);

	// ... data changing methods

	void deleteAll();

	void deleteById(long id);

	void save(Greeting greeting);

	void update(Greeting greeting);

}
