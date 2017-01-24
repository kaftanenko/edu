package edu.java.spring.rest.access.stateless.server.controller.greeting;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.java.spring.business.greeting.domain.Greeting;
import edu.java.spring.business.greeting.service.GreetingService;
import edu.java.spring.business.greeting.service.GreetingServiceImpl;

@RestController
public class GreetingRestServiceImpl {

	// ... dependency properties

	private final GreetingService greetingService = new GreetingServiceImpl();

	// ... REST methods

	@RequestMapping(value = "/greeting/", method = RequestMethod.GET)
	public ResponseEntity<List<Greeting>> listAll() {

		final List<Greeting> greetings = greetingService.findAll();

		if (greetings.isEmpty()) {
			return new ResponseEntity<List<Greeting>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Greeting>>(greetings, HttpStatus.OK);
	}

	@RequestMapping("/greeting")
	public Greeting getGreetingFor(@RequestParam(value = "name", defaultValue = "World") String name) {

		return greetingService.findByName(name);
	}

	@RequestMapping(value = "/greeting/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Greeting> getUser(@PathVariable("id") long id) {

		final Greeting greeting = greetingService.findById(id);

		if (greeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}

	@RequestMapping(value = "/greeting/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody Greeting greeting, UriComponentsBuilder ucBuilder) {

		if (greetingService.isExist(greeting)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		greetingService.save(greeting);

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/greeting/{id}").buildAndExpand(greeting.getId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/greeting/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Greeting> updateUser(@PathVariable("id") long id, @RequestBody Greeting greeting) {

		final Greeting currentGreeting = greetingService.findById(id);

		if (currentGreeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		// ... update currentGreeting properties from greeting
		greetingService.update(currentGreeting);

		return new ResponseEntity<Greeting>(currentGreeting, HttpStatus.OK);
	}

	@RequestMapping(value = "/greeting/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Greeting> deleteUser(@PathVariable("id") long id) {

		final Greeting greeting = greetingService.findById(id);

		if (greeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		greetingService.deleteById(id);

		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/greeting/", method = RequestMethod.DELETE)
	public ResponseEntity<Greeting> deleteAll() {

		greetingService.deleteAll();

		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}

}