package edu.java.spring.rest.access.stateless.client.service;

import org.springframework.web.client.RestTemplate;

import edu.java.spring.business.greeting.domain.Greeting;
import edu.java.spring.business.greeting.service.GreetingService;
import edu.java.spring.rest.access.stateless.client.service.type.GreetingMutableObject;

public class GreetingRestClientServiceImpl implements GreetingService {

	// ... dependency properties

	final RestTemplate restTemplate = new RestTemplate();

	// ... business methods

	@Override
	public Greeting getGreetingFor(String name) {

		final Greeting greeting = restTemplate.getForObject("http://localhost:9090/greeting",
				GreetingMutableObject.class);
		return greeting;
	}

}
