package edu.java.spring.business.greeting.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import edu.java.spring.business.greeting.domain.Greeting;

public class GreetingServiceImpl implements GreetingService {

	// ... constants

	private static final String GREETING_MESSAGE_TEMPLATE = "Hello, %s!";

	// ... properties

	private final AtomicLong counter = new AtomicLong();

	// ... business methods

	@Override
	public List<Greeting> findAll() {
		return Arrays.asList(new Greeting[] { findByName("Anonymous") });
	}

	@Override
	public Greeting findById(long id) {
		return findByName("Anonymous");
	}

	@Override
	public Greeting findByName(final String name) {

		final long id = counter.incrementAndGet();
		final String message = String.format(GREETING_MESSAGE_TEMPLATE, name);

		return new Greeting(id, message);
	}

	@Override
	public boolean isExist(Greeting greeting) {
		return true;
	}

	@Override
	public void deleteAll() {
		System.out.println("Called function: deleteAll()");
	}

	@Override
	public void deleteById(long id) {
		System.out.println("Called function: deleteById(" + id + ")");
	}

	@Override
	public void save(Greeting greeting) {
		System.out.println("Called function: save(greeting)");
	}

	@Override
	public void update(Greeting greeting) {
		System.out.println("Called function: update(greeting)");
	}

}
