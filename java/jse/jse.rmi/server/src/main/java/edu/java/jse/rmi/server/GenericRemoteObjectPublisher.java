package edu.java.jse.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GenericRemoteObjectPublisher {

	// ... properties

	public final Registry registry;

	// ... constructors

	public GenericRemoteObjectPublisher(final int rmiRegistryPort) {

		super();

		// ... initialize security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		// ... retrieve reference to the local RMI registry
		try {
			registry = LocateRegistry.getRegistry(rmiRegistryPort);
		} catch (RemoteException ex) {
			throw new RuntimeException(ex);
		}
	}

	// ... publisher methods

	public void publishRemoteObject(final String remoteObjectName, final Remote remoteObject, final int remoteObjectPort) {

		final String remoteObjectClassName = remoteObject.getClass().getSimpleName();

		System.out.println("Try to register " + remoteObjectClassName + " on the local RMI server...");
		try {

			// ... initialize remote object stub
			final Remote remoteObjectStub = UnicastRemoteObject.exportObject(remoteObject, remoteObjectPort);

			// ... try to register remote object stub on the local RMI server
			registry.rebind(remoteObjectName, remoteObjectStub);

			System.out.println("... " + remoteObjectClassName + " registering succeded.");
		} catch (RemoteException ex) {

			System.out.println("... " + remoteObjectClassName + " registering failed due to: " + ex.getMessage() + ".");
			throw new RuntimeException(ex);
		}
	}

}
