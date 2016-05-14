package osgi_bndtools.calculator.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import osgi_bndtools.calculator.service.api.ISimpleCalculatorService;

/**
 * <b>Unit 2</b>: ... demonstrates parameterized JUnit tests.
 * 
 * @author java.developer
 */
@RunWith(Parameterized.class)
public class Unit2_SimpleCalculator_Subtruction_ParameterizedTest {

	// ... properties

	private ISimpleCalculatorService<Long> calculatorUnderTest;

	// ... setUp/tearDown

	private final long arg1;

	private final long arg2;

	private final long expectedResult;

	// ... constructors

	public Unit2_SimpleCalculator_Subtruction_ParameterizedTest(long arg1, long arg2, long expectedResult) {

		calculatorUnderTest = new SimpleCalculatorService_LongType();

		this.arg1 = arg1;
		this.arg2 = arg2;
		this.expectedResult = expectedResult;
	}

	// ... test methods

	@Test
	public void test_Subtraction_Of() {

		// ... run operation
		final long result = calculatorUnderTest.sub(arg1, arg2);

		// ... postconditions
		assertEquals(expectedResult, result);
	}

	// ... test parameters generator

	@Parameterized.Parameters
	public static List<Object[]> testData() {

		final Object[][] testDataTriples_Arg1_Arg2_ExpectedResult = { { 8, 5, 3 }, { 3, 3, 0 }, { 0, 0, 0 },
				{ 1, 2, -1 }, { -3, -3, 0 }, { -8, -5, -3 } };

		return Arrays.asList(testDataTriples_Arg1_Arg2_ExpectedResult);
	}
}
