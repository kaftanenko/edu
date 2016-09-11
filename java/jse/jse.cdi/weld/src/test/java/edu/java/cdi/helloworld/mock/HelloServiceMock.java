package edu.java.cdi.helloworld.mock;

import javax.enterprise.inject.Alternative;

import edu.java.cdi.helloworld.impl.localized.LocalizedService;

@Alternative
@LocalizedService
public class HelloServiceMock {// implements IHelloService {

	public String sayHelloTo(String name) {
	
		return "Some mocked string.";
	}
	
}
