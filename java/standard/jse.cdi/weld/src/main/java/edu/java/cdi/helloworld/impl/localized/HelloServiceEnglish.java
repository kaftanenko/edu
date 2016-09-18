package edu.java.cdi.helloworld.impl.localized;

import edu.java.cdi.helloworld.api.IHelloService;

@LocalizedService(lang = SupportedLanguages.ENGLISH)
public class HelloServiceEnglish implements IHelloService {
	
	// ... business methods
	
	@Override
	public String sayHelloTo(String name) {
	
		return "Hello " + name + "!";
	}
	
}
