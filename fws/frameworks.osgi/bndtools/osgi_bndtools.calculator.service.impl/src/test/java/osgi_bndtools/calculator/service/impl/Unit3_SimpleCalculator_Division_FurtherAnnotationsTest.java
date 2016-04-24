package osgi_bndtools.calculator.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import osgi_bndtools.calculator.service.api.ISimpleCalculatorService;
import osgi_bndtools.calculator.service.impl.SimpleCalculatorService;

/**
 * <b>Unit 3</b>: ... demonstrates some further JUnit annotations and
 * parameters.
 * 
 * <ul>
 * <li>@Ignore("&lt;some-comment&gt;")</li>
 * <li>@Test(expected=&lt;some-exception-class&gt;, timeout=&lt;time-in-ms&gt;)
 * </li>
 * </ul>
 * 
 * @author java.developer
 */
public class Unit3_SimpleCalculator_Division_FurtherAnnotationsTest {

	// ... properties

	private ISimpleCalculatorService calculatorUnderTest;

	// ... setUp/tearDown

	@Before
	public void setUp() {

		calculatorUnderTest = new SimpleCalculatorService();
	}

	// ... test methods

	@Test
	@Ignore("... is unimportant yet. But don't abuse the @Ignore annotation!")
	public void test_Demonstrating_Use_Of_The_Ignore_Annotaion() {

		fail("... is not implemented yet.");
	}

	@Test(expected = ArithmeticException.class)
	public void test_Division_Of_Positive_By_Zero_Numbers_WithExpecting_ArithmeticException() {

		final long arg1 = 7;
		final long arg2 = 0;

		calculatorUnderTest.div(arg1, arg2);
	}

	@Test(expected = ArithmeticException.class)
	public void test_Division_Of_Negative_By_Zero_Numbers_WithExpecting_ArithmeticException() {

		final long arg1 = -7;
		final long arg2 = 0;

		calculatorUnderTest.div(arg1, arg2);
	}

	@Test
	public void test_Division_Of_Two_Positive_Numbers() {

		final long arg1 = 100;
		final long arg2 = 2;
		long expectedResult = 50;

		long result = calculatorUnderTest.div(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Division_Of_Two_Negative_Numbers() {

		final long arg1 = -99;
		final long arg2 = -3;
		long expectedResult = 33;

		long result = calculatorUnderTest.div(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Division_Of_Positive_By_Negative_Numbers() {

		final long arg1 = 95;
		final long arg2 = -5;
		long expectedResult = -19;

		long result = calculatorUnderTest.div(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test
	public void test_Division_Of_Negative_By_Positive_Numbers() {

		final long arg1 = -96;
		final long arg2 = 4;
		long expectedResult = -24;

		long result = calculatorUnderTest.div(arg1, arg2);

		assertEquals(expectedResult, result);
	}

	@Test(expected = ArithmeticException.class)
	public void test_Division_Of_By_Zero_Numbers() {

		final long arg1 = 94;
		final long arg2 = 0;

		calculatorUnderTest.div(arg1, arg2);
	}

	@Test
	public void test_Devide_For_MaxSupportingNumber_By_2() {

		final long arg1 = Long.MAX_VALUE;
		final long arg2 = 2;

		long result = calculatorUnderTest.div(arg1, arg2);

		assertTrue(result > 0);
	}

	@Test
	public void test_Devide_For_MinSupportingNumber_By_2() {

		final long arg1 = Long.MIN_VALUE;
		final long arg2 = 2;

		long result = calculatorUnderTest.div(arg1, arg2);

		assertTrue(result < 0);
	}

}
