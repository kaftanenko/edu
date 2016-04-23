package edu.java.junit.calculator.api;

public interface IScientificCalculatorService extends ISimpleCalculatorService {

	// ... business methods

	public long ln(long arg);

	public long lg(long arg);

	public long log(long arg1, long arg2);

}
