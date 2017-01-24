package edu.java.spring.rest.access.stateless.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.java.spring.business.greeting.domain.Greeting;
import edu.java.spring.business.greeting.service.GreetingService;
import edu.java.spring.business.greeting.service.GreetingServiceImpl;

@RestController
public class GreetingRestServiceImpl {

	// ... dependency properties

	private final GreetingService greetingService = new GreetingServiceImpl();

	// ... REST methods

	@RequestMapping("/greeting")
	public Greeting getGreetingFor(@RequestParam(value = "name", defaultValue = "World") String name) {

		return greetingService.findByName(name);
	}

}