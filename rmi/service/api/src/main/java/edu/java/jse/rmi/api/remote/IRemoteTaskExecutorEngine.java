package edu.java.jse.rmi.api.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.java.jse.rmi.api.ITask;

public interface IRemoteTaskExecutorEngine extends Remote {

	public static class KEYS {

		// ... constants

		public static final String SERVICE_NAME = "RemoteTaskExecutorEngine";

	}

	// ... business methods

	public <T> T executeTask(ITask<T> task) throws RemoteException;

}
