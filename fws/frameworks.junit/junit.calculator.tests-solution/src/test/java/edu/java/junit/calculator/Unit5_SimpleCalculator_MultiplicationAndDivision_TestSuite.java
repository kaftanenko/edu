package edu.java.junit.calculator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * <b>Unit 5</b>: ... demonstrates JUnit tests suite.
 * 
 * @author java.developer
 */
@RunWith(Suite.class)
@SuiteClasses(value={Unit3_SimpleCalculator_Division_FurtherAnnotationsTest.class, Unit4_SimpleCalculator_Multiplication_SpringDependencyInjectionTest.class})
public class Unit5_SimpleCalculator_MultiplicationAndDivision_TestSuite {

}
