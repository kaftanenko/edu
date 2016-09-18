package edu.java.jse.rmi.server;

import java.rmi.Remote;

import edu.java.jse.rmi.api.ITaskExecutorEngine;
import edu.java.jse.rmi.api.remote.IRemoteTaskExecutorEngine;
import edu.java.jse.rmi.service.TaskExecutorEngineImpl;

public class RemoteTaskExecutorPublisherMain {

	// ... constants

	private static final int DEFAULT_RMI_REGISTRY_PORT = 1099;

	private static final int SOME_REMOTE_TASK_EXECUTOR_PORT = 7000; // = 0 for
																	// any
																	// port

	// ... main method - service starter

	public static void main(String[] args) {

		// ... verify command line parameters
		final int rmiRegistryPort;
		if (args.length == 0) {
			rmiRegistryPort = DEFAULT_RMI_REGISTRY_PORT;
		} else {
			final String rmiRegistryPortParam = args[0];
			// TODO aka: ... handle NumberFormatException
			rmiRegistryPort = Integer.parseInt(rmiRegistryPortParam);
		}

		// ... initialize remote object
		final RemoteTaskExecutorImpl remoteTaskExecutor = new RemoteTaskExecutorImpl();
		final ITaskExecutorEngine taskExecutor = new TaskExecutorEngineImpl();
		remoteTaskExecutor.setTaskExecutor(taskExecutor);

		// ... initialize remote object publisher
		final GenericRemoteObjectPublisher rmiPublisher = new GenericRemoteObjectPublisher(rmiRegistryPort);

		// ... publish remote object on the local RMI server
		final String remoteObjectName = IRemoteTaskExecutorEngine.KEYS.SERVICE_NAME;
		final Remote remoteObject = remoteTaskExecutor;
		final int remoteObjectPort = SOME_REMOTE_TASK_EXECUTOR_PORT;

		rmiPublisher.publishRemoteObject(remoteObjectName, remoteObject, remoteObjectPort);
	}

}
