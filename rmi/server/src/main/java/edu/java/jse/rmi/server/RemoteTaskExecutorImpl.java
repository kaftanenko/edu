package edu.java.jse.rmi.server;

import java.rmi.RemoteException;

import edu.java.jse.rmi.api.ITask;
import edu.java.jse.rmi.api.ITaskExecutorEngine;
import edu.java.jse.rmi.api.remote.IRemoteTaskExecutorEngine;

public class RemoteTaskExecutorImpl implements IRemoteTaskExecutorEngine {

	// ... properties

	private ITaskExecutorEngine taskExecutor;

	// ... business methods

	@Override
	public <T> T executeTask(ITask<T> task) throws RemoteException {

		return taskExecutor.executeTask(task);
	}

	// ... dependency injection methods

	public void setTaskExecutor(ITaskExecutorEngine taskExecutor) {

		this.taskExecutor = taskExecutor;
	}

}
