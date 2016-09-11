package edu.java.cdi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import edu.java.cdi.helloworld.api.IHelloService;
import edu.java.cdi.helloworld.impl.localized.LocalizedService;
import edu.java.cdi.helloworld.impl.localized.SupportedLanguages;
import edu.java.cdi.util.WeldContext;

public class App {
	
	// ... properties
	
	@Inject
	@LocalizedService(lang = SupportedLanguages.RUSSIAN)
	private IHelloService helloService;
	
	// ... life cycle methods
	
	public void start() {
	
		final String helloGreeting = helloService.sayHelloTo("Мир");
		System.out.println(helloGreeting);
	}
	
	// ...
	
	@PostConstruct
	public void init() {
	
		System.out.println("Application started.");
	}
	
	@PreDestroy
	public void dispose() {
	
		System.out.println("Application finished.");
	}
	
	// ... MAIN method
	
	public static void main(String[] args) {
	
		final App app = WeldContext.INSTANCE.getBean(App.class);
		app.start();
		WeldContext.INSTANCE.shutdown();
		
		System.gc();
	}
	
}
