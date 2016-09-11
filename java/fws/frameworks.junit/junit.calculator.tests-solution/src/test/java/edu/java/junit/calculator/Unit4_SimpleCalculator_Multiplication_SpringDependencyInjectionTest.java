package edu.java.junit.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.java.junit.calculator.api.ISimpleCalculatorService;
import edu.java.junit.calculator.util.SpringTestHelper;

/**
 * <b>Unit 4</b>: ... demonstrates JUnit initialization using dependency
 * injection engine (Spring).
 * 
 * @author java.developer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ SpringTestHelper.CONTEXT_LOCATION, SpringTestHelper.TEST_CONTEXT_LOCATION })
public class Unit4_SimpleCalculator_Multiplication_SpringDependencyInjectionTest {

	// ... properties

	@Autowired
	private ISimpleCalculatorService calculatorUnderTest;

	// ... test methods

	@Test
	public void test_Multiplication_Of_Two_Positive_Numbers() {

		final long arg1 = 2;
		final long arg2 = 3;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = 6;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Multiplication_Of_Two_Negative_Numbers() {

		final long arg1 = -4;
		final long arg2 = -5;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = 20;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Multiplication_Of_Negative_And_Positive_Numbers() {

		final long arg1 = -6;
		final long arg2 = 7;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = -42;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Multiplication_Of_Positive_And_Zero_Numbers() {

		final long arg1 = 7;
		final long arg2 = 0;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = 0;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Multiplication_Of_Negative_And_Zero_Numbers() {

		final long arg1 = -7;
		final long arg2 = 0;

		long result = calculatorUnderTest.mul(arg1, arg2);

		long expectedResult = 0;
		assertEquals(expectedResult, result);
	}

	@Test(expected = ArithmeticException.class)
	// ... due to some kind of number overflow.
	public void test_Multiplication_Of_MaxSupportingLongValue_And_2_WithExpecting_ArithmeticException() {

		final long arg1 = Long.MAX_VALUE;
		final long arg2 = 2;

		calculatorUnderTest.mul(arg1, arg2);
	}

	@Test(expected = ArithmeticException.class)
	// ... due to some kind of number overflow.
	public void test_Multiplication_Of_MinSupportingLongValue_And_2_WithExpecting_ArithmeticException() {

		final long arg1 = Long.MIN_VALUE;
		final long arg2 = 2;

		calculatorUnderTest.mul(arg1, arg2);
	}

}
