package edu.java.jse.rmi.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.java.jse.rmi.api.ITask;
import edu.java.jse.rmi.api.ITaskExecutorEngine;

public class TaskExecutorEngineImplTest {

	// ... constants

	private static final String SUCCESS_MESSAGE = "success message";

	// ... properties

	private ITaskExecutorEngine taskExecutor;

	// ... setUp/tearDown methods

	@Before
	public void setUp() {

		taskExecutor = new TaskExecutorEngineImpl();
	}

	// ... test methods

	@Test
	public void testTaskExecutor() {

		// ... initialize test data
		final ITask<String> testTask = new ITask<String>() {

			@Override
			public String execute() {

				return SUCCESS_MESSAGE;
			}

		};

		// ... verify preconditions

		// ... run operation under test
		final String taskResult = taskExecutor.executeTask(testTask);

		// ... verify postconditions
		assertEquals(SUCCESS_MESSAGE, taskResult);
	}

}
