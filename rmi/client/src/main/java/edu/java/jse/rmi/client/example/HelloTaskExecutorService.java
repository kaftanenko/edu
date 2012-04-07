package edu.java.jse.rmi.client.example;

import edu.java.jse.rmi.api.ITask;
import edu.java.jse.rmi.api.ITaskExecutorEngine;

public class HelloTaskExecutorService {

	// ... properties

	private ITaskExecutorEngine taskExecutor;

	// ... business methods

	public String executeHelloTask(final String personName) {

		final ITask<String> helloTask = new HelloTask(personName);
		return taskExecutor.executeTask(helloTask);
	}

	// ... dependency injection methods

	public void setTaskExecutor(ITaskExecutorEngine taskExecutor) {

		this.taskExecutor = taskExecutor;
	}

}
