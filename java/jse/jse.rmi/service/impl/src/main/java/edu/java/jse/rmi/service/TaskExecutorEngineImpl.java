package edu.java.jse.rmi.service;

import edu.java.jse.rmi.api.ITaskExecutorEngine;
import edu.java.jse.rmi.api.ITask;

public class TaskExecutorEngineImpl implements ITaskExecutorEngine {

	// ... business methods

	@Override
	public <T> T executeTask(ITask<T> task) {

		return task.execute();
	}

}
