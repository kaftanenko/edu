package edu.java.jse.rmi.api;

public interface ITaskExecutorEngine {

	// ... business methods

	public <T> T executeTask(ITask<T> task);

}
