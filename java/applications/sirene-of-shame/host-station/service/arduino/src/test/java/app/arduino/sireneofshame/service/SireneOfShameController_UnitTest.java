package app.arduino.sireneofshame.service;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import app.arduino.sireneofshame.service.host.api.ESireneOfShameAlarmLevel;
import app.arduino.sireneofshame.service.host.api.SireneOfShameHostController;
import app.arduino.sireneofshame.service.host.api.SireneOfShameHostControllerEventsListener;
import app.arduino.sireneofshame.service.host.impl.dummy.DummySireneOfShameHostController;

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
		final ESireneOfShameAlarmLevel expectedValue = ESireneOfShameAlarmLevel.GREENBLUE;

		// ... call service under test
		final ESireneOfShameAlarmLevel resultValue = serviceUnderTest.getCurrentAlarmLevel();

		// ... verify post-conditions
		Assertions.assertThat(resultValue).isEqualTo(expectedValue);
	}

	@Test
	public void test_SetState_Succeeds() {

		// ... prepare test data
		final ESireneOfShameAlarmLevel expectedValue = ESireneOfShameAlarmLevel.RED;

		// ... call service under test
		serviceUnderTest.setAlarmLevelTo(expectedValue);

		// ... verify post-conditions
		verify_ServiceState_Is(serviceUnderTest, expectedValue);
	}

	@Test
	public void test_Subscribe_With_Following_SetState_Succeeds() {

		// ... prepare test data
		final ESireneOfShameAlarmLevel initialValue = ESireneOfShameAlarmLevel.GREENBLUE;
		final ESireneOfShameAlarmLevel expectedValue = ESireneOfShameAlarmLevel.RED;

		// ... call service under test
		serviceUnderTest.subscribe(eventsListenerUnderTest);
		serviceUnderTest.setAlarmLevelTo(ESireneOfShameAlarmLevel.RED);

		// ... verify post-conditions
		verify_ServiceState_Is(serviceUnderTest, expectedValue);

		Mockito.verify(eventsListenerUnderTest).onStateChanged(initialValue, expectedValue);
		Mockito.verifyNoMoreInteractions(eventsListenerUnderTest);
	}

	@Test
	public void test_Unsubscribe_With_Following_SetState_Succeeds() {

		// ... prepare test data
		final ESireneOfShameAlarmLevel initialValue = ESireneOfShameAlarmLevel.GREENBLUE;
		final ESireneOfShameAlarmLevel expectedValue = ESireneOfShameAlarmLevel.RED;

		verify_ServiceState_Is(serviceUnderTest, initialValue);

		serviceUnderTest.subscribe(eventsListenerUnderTest);

		// ... call service under test
		serviceUnderTest.unsubscribe(eventsListenerUnderTest);
		serviceUnderTest.setAlarmLevelTo(ESireneOfShameAlarmLevel.RED);

		// ... verify post-conditions
		verify_ServiceState_Is(serviceUnderTest, expectedValue);

		Mockito.verifyNoMoreInteractions(eventsListenerUnderTest);
	}

	// ... verify methods

	private static void verify_ServiceState_Is(final SireneOfShameHostController service,
			final ESireneOfShameAlarmLevel expectedValue) {

		final ESireneOfShameAlarmLevel currentValue = service.getCurrentAlarmLevel();
		Assertions.assertThat(currentValue).isEqualTo(expectedValue);
	}

}
