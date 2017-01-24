package edu.java.spring.business.greeting.service;

import edu.java.spring.business.greeting.domain.Greeting;

public interface GreetingService {

	// ... business methods

	Greeting getGreetingFor(String name);

}
