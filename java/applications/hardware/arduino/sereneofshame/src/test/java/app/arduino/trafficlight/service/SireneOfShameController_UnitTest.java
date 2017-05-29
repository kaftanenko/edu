package app.arduino.trafficlight.service;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.api.SireneOfShameController;
import app.arduino.sereneofshame.service.impl.dummy.SireneOfShameControllerDummyImpl;

public class SireneOfShameController_UnitTest {

	// ... properties

	private SireneOfShameController serviceUnderTest;

	// ... setUp()/tearDown() methods

	@BeforeMethod(alwaysRun = true)
	public void setUp() {

		serviceUnderTest = new SireneOfShameControllerDummyImpl();
	}

	// ... test methods

	@Test
	public void test_DefaultConfig() {

		// ... prepare test data
		final ESireneOfShameState expectedValue = ESireneOfShameState.GREEN_BLUE;

		// ... call service under test
		final ESireneOfShameState resultValue = serviceUnderTest.getState();

		// ... verify post-conditions
		Assertions.assertThat(resultValue).isEqualTo(expectedValue);
	}

	@Test
	public void test_SetState_Succeeds() {

		// ... prepare test data
		final ESireneOfShameState expectedValue = ESireneOfShameState.RED;

		// ... call service under test
		serviceUnderTest.setState(ESireneOfShameState.RED);

		// ... verify post-conditions
		final ESireneOfShameState currentValue = serviceUnderTest.getState();
		Assertions.assertThat(currentValue).isEqualTo(expectedValue);
	}

}
