package edu.java.spring.business.greeting.service;

import java.util.concurrent.atomic.AtomicLong;

import edu.java.spring.business.greeting.domain.Greeting;

public class GreetingServiceImpl implements GreetingService {

	// ... constants

	private static final String GREETING_MESSAGE_TEMPLATE = "Hello, %s!";

	// ... properties

	private final AtomicLong counter = new AtomicLong();

	// ... business methods

	@Override
	public Greeting getGreetingFor(final String name) {

		final long id = counter.incrementAndGet();
		final String message = String.format(GREETING_MESSAGE_TEMPLATE, name);

		return new Greeting(id, message);
	}

}
