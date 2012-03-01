package edu.java.junit.calc;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import edu.java.junit.calc.api.ISimpleCalculatorApp;
import edu.java.junit.calc.impl.SimpleCalculatorApp;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SimpleCalculatorAdditionTest {

	// ... properties

	private static ISimpleCalculatorApp calculatorUnderTest;

	private final long operationArg1;

	private final long operationArg2;

	private final long expectedOperationResult;

	// ... constructors

	public SimpleCalculatorAdditionTest(long arg1, long arg2, long expectedResult) {

		calculatorUnderTest = new SimpleCalculatorApp();

		this.operationArg1 = arg1;
		this.operationArg2 = arg2;
		this.expectedOperationResult = expectedResult;
	}

	// ... test methods

	@Test
	public void test_AddOperation() {

		// ... run operation
		final long result = calculatorUnderTest.add(operationArg1, operationArg2);

		// ... postconditions
		assertEquals(expectedOperationResult, result);
	}

	// ... test parameters generator

	@Parameterized.Parameters
	public static List<Object[]> testData() {

		final Object[][] testDataTriplesArg1Arg2ExpectedResult = { { 1, 2, 3 }, { -3, -4, -7 }, { -5, 6, 1 }, { 7, 0, 7 } };

		return Arrays.asList(testDataTriplesArg1Arg2ExpectedResult);
	}
}
