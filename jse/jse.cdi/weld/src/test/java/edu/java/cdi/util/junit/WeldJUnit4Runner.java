package edu.java.cdi.util.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import edu.java.cdi.util.WeldContext;

public class WeldJUnit4Runner extends BlockJUnit4ClassRunner {
	
	// ... constructors
	
	public WeldJUnit4Runner(Class<Object> clazz) throws InitializationError {
	
		super(clazz);
	}
	
	// ... business methods
	
	@Override
	protected Object createTest() {
	
		final Class<?> test = getTestClass().getJavaClass();
		return WeldContext.INSTANCE.getBean(test);
	}
	
}