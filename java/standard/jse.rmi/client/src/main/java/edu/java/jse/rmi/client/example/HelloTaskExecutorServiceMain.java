package edu.java.jse.rmi.client.example;

import edu.java.jse.rmi.api.ITaskExecutorEngine;
import edu.java.jse.rmi.client.remote.RemoteTaskExecutorClient;

public class HelloTaskExecutorServiceMain {

	// ... main method - example starter

	public static void main(String[] args) {

		// ... verify command line parameters
		// FIXME aka: ... handle possible exceptions
		final String rmiRegistryAddress = args[0];
		final String personName = args[1];

		System.out.println("Try to execute the HelloTask for the person name \"" + personName + "\"...");

		// ... call the HelloTask service
		try {
			// ... initialize service
			final HelloTaskExecutorService helloTaskExecutor = new HelloTaskExecutorService();
			final ITaskExecutorEngine taskExecutor = new RemoteTaskExecutorClient(rmiRegistryAddress);
			helloTaskExecutor.setTaskExecutor(taskExecutor);

			// ... call the service
			final String greeting = helloTaskExecutor.executeHelloTask(personName);

			// ... consume service result
			System.out.println("... task execution succeded with the result: \"" + greeting + "\".");
		} catch (RuntimeException ex) {

			// ... consume service exception info
			System.out.println("... task execution failed due to: " + ex.getMessage() + "\".");
		}
	}
}
