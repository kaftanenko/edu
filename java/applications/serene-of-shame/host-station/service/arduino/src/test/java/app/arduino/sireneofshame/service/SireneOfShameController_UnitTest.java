package app.arduino.sireneofshame.service;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import app.arduino.sereneofshame.service.host.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.host.api.SireneOfShameHostController;
import app.arduino.sereneofshame.service.host.api.SireneOfShameHostControllerEventsListener;
import app.arduino.sereneofshame.service.host.impl.dummy.DummySireneOfShameHostController;

public class SireneOfShameController_UnitTest {

	// ... properties

	private SireneOfShameHostController serviceUnderTest;

	private SireneOfShameHostControllerEventsListener eventsListenerUnderTest;

	// ... setUp()/tearDown() methods

	@BeforeMethod(alwaysRun = true)
	public void setUp() {

		serviceUnderTest = new DummySireneOfShameHostController();
		eventsListenerUnderTest = Mockito.mock(SireneOfShameHostControllerEventsListener.class);
	}

	// ... test methods

	@Test
	public void test_DefaultConfig() {

		// ... prepare test data
		final ESireneOfShameState expectedValue = ESireneOfShameState.GREENBLUE;

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
		serviceUnderTest.setState(expectedValue);

		// ... verify post-conditions
		verify_ServiceState_Is(serviceUnderTest, expectedValue);
	}

	@Test
	public void test_Subscribe_With_Following_SetState_Succeeds() {

		// ... prepare test data
		final ESireneOfShameState initialValue = ESireneOfShameState.GREENBLUE;
		final ESireneOfShameState expectedValue = ESireneOfShameState.RED;

		// ... call service under test
		serviceUnderTest.subscribe(eventsListenerUnderTest);
		serviceUnderTest.setState(ESireneOfShameState.RED);

		// ... verify post-conditions
		verify_ServiceState_Is(serviceUnderTest, expectedValue);

		Mockito.verify(eventsListenerUnderTest).onStateChanged(initialValue, expectedValue);
		Mockito.verifyNoMoreInteractions(eventsListenerUnderTest);
	}

	@Test
	public void test_Unsubscribe_With_Following_SetState_Succeeds() {

		// ... prepare test data
		final ESireneOfShameState initialValue = ESireneOfShameState.GREENBLUE;
		final ESireneOfShameState expectedValue = ESireneOfShameState.RED;

		verify_ServiceState_Is(serviceUnderTest, initialValue);

		serviceUnderTest.subscribe(eventsListenerUnderTest);

		// ... call service under test
		serviceUnderTest.unsubscribe(eventsListenerUnderTest);
		serviceUnderTest.setState(ESireneOfShameState.RED);

		// ... verify post-conditions
		verify_ServiceState_Is(serviceUnderTest, expectedValue);

		Mockito.verifyNoMoreInteractions(eventsListenerUnderTest);
	}

	// ... verify methods

	private static void verify_ServiceState_Is(final SireneOfShameHostController service,
			final ESireneOfShameState expectedValue) {

		final ESireneOfShameState currentValue = service.getState();
		Assertions.assertThat(currentValue).isEqualTo(expectedValue);
	}

}
