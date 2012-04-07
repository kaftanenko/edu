package edu.java.jse.rmi.client.remote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import edu.java.jse.rmi.api.ITask;
import edu.java.jse.rmi.api.ITaskExecutorEngine;
import edu.java.jse.rmi.api.remote.IRemoteTaskExecutorEngine;

public class RemoteTaskExecutorClient implements ITaskExecutorEngine {

	// ... properties

	private final IRemoteTaskExecutorEngine remoteTaskExecutor;

	// ... constructors

	public RemoteTaskExecutorClient(final String remoteRmiRegistryAddress) {

		// ... initialize security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		// ... retrieve remote service reference
		try {
			final Registry registry = LocateRegistry.getRegistry(remoteRmiRegistryAddress);

			remoteTaskExecutor = (IRemoteTaskExecutorEngine) registry
					.lookup(IRemoteTaskExecutorEngine.KEYS.SERVICE_NAME);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	// ... business methods

	@Override
	public <T> T executeTask(ITask<T> task) {

		try {
			return remoteTaskExecutor.executeTask(task);
		} catch (RemoteException ex) {
			throw new RuntimeException(ex);
		}
	}

}
