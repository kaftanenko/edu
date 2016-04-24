package osgi_bndtools.calculator.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import osgi_bndtools.calculator.service.api.ISimpleCalculatorService;
import osgi_bndtools.calculator.service.impl.SimpleCalculatorService;

/**
 * <b>Unit 1</b>: ... JUnit simple JUnit Test.
 * 
 * @author java.developer
 */
public class Unit1_SimpleCalculator_Addition_SimpleTest {

	// ... properties

	private ISimpleCalculatorService calculatorUnderTest;

	// ... setUp/tearDown

	@Before
	public void setUp() {

		calculatorUnderTest = new SimpleCalculatorService();
	}

	// ... test methods

	@Test
	public void test_Addition_Of_Two_Positive_Numbers() {

		final long arg1 = 2;
		final long arg2 = 3;
		long expectedResult = 5;

		long result = calculatorUnderTest.add(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Addition_Of_Two_Negative_Numbers() {

		final long arg1 = -2;
		final long arg2 = -3;
		long expectedResult = -5;

		long result = calculatorUnderTest.add(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Addition_Of_Positive_And_Zero_Numbers() {

		final long arg1 = 1;
		final long arg2 = 0;
		long expectedResult = 1;

		long result = calculatorUnderTest.add(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Addition_Of_Two_Zero_Numbers() {

		final long arg1 = 0;
		final long arg2 = 0;
		long expectedResult = 0;

		long result = calculatorUnderTest.add(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Addition_Of_Negative_And_Zero_Numbers() {

		final long arg1 = -1;
		final long arg2 = 0;
		long expectedResult = -1;

		long result = calculatorUnderTest.add(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Addition_Of_Negative_And_Positive_Numbers_WithExpected_Zero_Result() {

		final long arg1 = -5;
		final long arg2 = 5;
		long expectedResult = 0;

		long result = calculatorUnderTest.add(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Addition_Of_Negative_And_Positive_Numbers_WithExpected_Positive_Result() {

		final long arg1 = -5;
		final long arg2 = 8;
		long expectedResult = 3;

		long result = calculatorUnderTest.add(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Addition_Of_Negative_And_Positive_Numbers_WithExpected_Negative_Result() {

		final long arg1 = -8;
		final long arg2 = 5;
		long expectedResult = -3;

		long result = calculatorUnderTest.add(arg1, arg2);

		assertEquals(expectedResult, result);
	}

}
