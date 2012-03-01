package edu.java.junit.calc;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.java.junit.calc.api.ISimpleCalculatorApp;
import edu.java.junit.calc.util.SpringTestHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ SpringTestHelper.CONTEXT_LOCATION, SpringTestHelper.TEST_CONTEXT_LOCATION })
public class SimpleCalculatorDivisionTest {

	// ... properties

	@Autowired
	private ISimpleCalculatorApp calculatorUnderTest;

	// ... test methods

	@Test
	public void test_Divide_Two_Positive_NoneZero_Numbers() {

		final long arg1 = 100;
		final long arg2 = 2;

		long result = calculatorUnderTest.div(arg1, arg2);

		long expectedResult = 50;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Divide_Two_Negative_NoneZero_Numbers() {

		final long arg1 = -99;
		final long arg2 = -3;

		long result = calculatorUnderTest.div(arg1, arg2);

		long expectedResult = 33;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Divide_Negative_Through_Positive_NoneZero_Numbers() {

		final long arg1 = -96;
		final long arg2 = 4;

		long result = calculatorUnderTest.div(arg1, arg2);

		long expectedResult = -24;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Divide_Positive_Through_Negative_NoneZero_Numbers() {

		final long arg1 = 95;
		final long arg2 = -5;

		long result = calculatorUnderTest.div(arg1, arg2);

		long expectedResult = -19;
		assertEquals(expectedResult, result);
	}

	@Test(expected = ArithmeticException.class)
	public void test_Divide_NoneZero_Through_Zero_Numbers() {

		final long arg1 = 94;
		final long arg2 = 0;

		calculatorUnderTest.div(arg1, arg2);
	}

	@Test(expected = ArithmeticException.class)
	public void test_Divide_Zero_Through_NoneZero_Numbers() {

		final long arg1 = 7;
		final long arg2 = 0;

		long result = calculatorUnderTest.div(arg1, arg2);

		long expectedResult = 0;
		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Devide_For_MaxSupportingNumber_Through_2() {

		final long arg1 = Long.MAX_VALUE;
		final long arg2 = 2;

		long result = calculatorUnderTest.div(arg1, arg2);

		assertTrue(result > 0);
	}

	@Test
	public void test_Devide_For_MinSupportingNumber_Through_2() {

		final long arg1 = Long.MIN_VALUE;
		final long arg2 = 2;

		long result = calculatorUnderTest.div(arg1, arg2);

		assertTrue(result < 0);
	}

	@Test
	@Ignore("... is unimportant yet. But don't abuse the @Ignore annotation!")
	public void test_Rounding_For_Actually_Restfull_Results() {

		fail("... is not implemented yet.");
	}

}
