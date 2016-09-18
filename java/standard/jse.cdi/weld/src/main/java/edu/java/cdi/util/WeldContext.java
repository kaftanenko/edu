package edu.java.cdi.util;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class WeldContext {
	
	// ... constants
	
	public static final WeldContext INSTANCE = new WeldContext();
	
	// ... properties
	
	private final Weld weld;
	private final WeldContainer container;
	
	// ... constructors
	
	private WeldContext() {
	
		this.weld = new Weld();
		this.container = weld.initialize();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
			
				shutdown();
			}
		});
	}
	
	// ... business methods
	
	public <T> T getBean(Class<T> type) {
	
		return container.instance().select(type).get();
	}
	
	public void shutdown() {
	
		weld.shutdown();
	}
	
}