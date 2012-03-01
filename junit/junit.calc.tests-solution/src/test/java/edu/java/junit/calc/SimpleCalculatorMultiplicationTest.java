package edu.java.junit.calc;

import org.junit.Before;
import org.junit.Test;

import edu.java.junit.calc.api.ISimpleCalculatorApp;
import edu.java.junit.calc.impl.SimpleCalculatorApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SimpleCalculatorMultiplicationTest {

	// ... properties

	private static ISimpleCalculatorApp calculatorUnderTest;

	// ... setUp/tearDown

	@Before
	public void setUp() {

		calculatorUnderTest = new SimpleCalculatorApp();
	}

	@Test
	public void test_Multiply_Two_Positive_NoneZero_Numbers() {

		final long arg1 = 2;
		final long arg2 = 3;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = 6;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Multiply_Two_Negative_NoneZero_Numbers() {

		final long arg1 = -4;
		final long arg2 = -5;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = 20;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Multiply_Negative_And_Positive_NoneZero_Numbers() {

		final long arg1 = -6;
		final long arg2 = 7;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = -42;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Multiply_NoneZero_And_Zero_Numbers() {

		final long arg1 = 7;
		final long arg2 = 0;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = 0;
		assertEquals(expectedResult, result);
	}

	@Test(expected = Exception.class)
	public void test_Multiply_MaxSupportingLongValue_And_2() {

		final long arg1 = Long.MAX_VALUE;
		final long arg2 = 2;

		calculatorUnderTest.mul(arg1, arg2);

		fail("... due to some kind of number overflow exception was expected.");
	}

	@Test(expected = Exception.class)
	public void test_Multiply_MinSupportingLongValue_And_2() {

		final long arg1 = Long.MIN_VALUE;
		final long arg2 = 2;

		calculatorUnderTest.mul(arg1, arg2);

		fail("... due to some kind of number overflow exception was expected.");
	}

}
