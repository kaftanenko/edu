package edu.java.junit.calc.impl;

import edu.java.junit.calc.api.ISimpleCalculatorApp;

public class SimpleCalculatorApp implements ISimpleCalculatorApp {

	// ... business methods

	@Override
	public long add(long arg1, long arg2) {

		return arg1 + arg2;
	}

	@Override
	public long div(long arg1, long arg2) {

		return arg1 / arg2;
	}

	@Override
	public long mul(long arg1, long arg2) {

		return arg1 * arg2;
	}

	@Override
	public long sub(long arg1, long arg2) {

		return arg1 - arg2;
	}

}
