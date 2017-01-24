package edu.java.spring.rest.access.stateless.client.service;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.java.spring.business.greeting.domain.Greeting;
import edu.java.spring.business.greeting.service.GreetingService;
import edu.java.spring.rest.access.stateless.client.service.type.GreetingMutableObject;

public class GreetingRestClientServiceImpl implements GreetingService {

	// ... dependency properties

	final RestTemplate restTemplate = new RestTemplate();

	// ... business methods

	@Override
	public List<Greeting> findAll() {
		throw handleUnimplementedYetException();
	}

	@Override
	public Greeting findById(long id) {
		throw handleUnimplementedYetException();
	}

	@Override
	public Greeting findByName(String name) {

		final Greeting greeting = restTemplate.getForObject("http://localhost:9090/greeting",
				GreetingMutableObject.class);
		return greeting;
	}

	@Override
	public boolean isExist(Greeting greeting) {
		throw handleUnimplementedYetException();
	}

	@Override
	public void deleteAll() {
		throw handleUnimplementedYetException();
	}

	@Override
	public void deleteById(long id) {
		throw handleUnimplementedYetException();
	}

	@Override
	public void save(Greeting greeting) {
		throw handleUnimplementedYetException();
	}

	@Override
	public void update(Greeting greeting) {
		throw handleUnimplementedYetException();
	}

	// ... helper methods

	private RuntimeException handleUnimplementedYetException() {
		throw new RuntimeException("Unimplemented yet.");
	}

}
