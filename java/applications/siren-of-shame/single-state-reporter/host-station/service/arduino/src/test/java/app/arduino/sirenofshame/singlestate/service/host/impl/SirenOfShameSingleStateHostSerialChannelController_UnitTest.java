package app.arduino.sirenofshame.singlestate.service.host.impl;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import app.arduino.sirenofshame.singlestate.service.host.api.SirenOfShameSingleStateHostController;
import app.arduino.sirenofshame.singlestate.service.host.api.SirenOfShameSingleStateHostControllerEventsListener;
import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.singlestate.service.host.impl.dummy.DummySirenOfShameSingleStateHostController;

public class SirenOfShameSingleStateHostSerialChannelController_UnitTest {

  // ... properties

  private SirenOfShameSingleStateHostController serviceUnderTest;

  private SirenOfShameSingleStateHostControllerEventsListener eventsListenerUnderTest;

  // ... setUp()/tearDown() methods

  @BeforeMethod(alwaysRun = true)
  public void setUp() {

    serviceUnderTest = new DummySirenOfShameSingleStateHostController();
    eventsListenerUnderTest = Mockito.mock(SirenOfShameSingleStateHostControllerEventsListener.class);
  }

  // ... test methods

  @Test
  public void test_DefaultConfig() {

    // ... prepare test data
    final ESirenOfShameAlarmLevel expectedValue = ESirenOfShameAlarmLevel.GREENBLUE;

    // ... call service under test
    final ESirenOfShameAlarmLevel resultValue = serviceUnderTest.getCurrentAlarmLevel();

    // ... verify post-conditions
    Assertions.assertThat(resultValue).isEqualTo(expectedValue);
  }

  @Test
  public void test_SetState_Succeeds() {

    // ... prepare test data
    final ESirenOfShameAlarmLevel expectedValue = ESirenOfShameAlarmLevel.RED;

    // ... call service under test
    serviceUnderTest.setAlarmLevelTo(expectedValue);

    // ... verify post-conditions
    verify_ServiceState_Is(serviceUnderTest, expectedValue);
  }

  @Test
  public void test_Subscribe_With_Following_SetState_Succeeds() {

    // ... prepare test data
    final ESirenOfShameAlarmLevel initialValue = ESirenOfShameAlarmLevel.GREENBLUE;
    final ESirenOfShameAlarmLevel expectedValue = ESirenOfShameAlarmLevel.RED;

    // ... call service under test
    serviceUnderTest.subscribe(eventsListenerUnderTest);
    serviceUnderTest.setAlarmLevelTo(ESirenOfShameAlarmLevel.RED);

    // ... verify post-conditions
    verify_ServiceState_Is(serviceUnderTest, expectedValue);

    Mockito.verify(eventsListenerUnderTest).onStateChanged(initialValue, expectedValue);
    Mockito.verifyNoMoreInteractions(eventsListenerUnderTest);
  }

  @Test
  public void test_Unsubscribe_With_Following_SetState_Succeeds() {

    // ... prepare test data
    final ESirenOfShameAlarmLevel initialValue = ESirenOfShameAlarmLevel.GREENBLUE;
    final ESirenOfShameAlarmLevel expectedValue = ESirenOfShameAlarmLevel.RED;

    verify_ServiceState_Is(serviceUnderTest, initialValue);

    serviceUnderTest.subscribe(eventsListenerUnderTest);

    // ... call service under test
    serviceUnderTest.unsubscribe(eventsListenerUnderTest);
    serviceUnderTest.setAlarmLevelTo(ESirenOfShameAlarmLevel.RED);

    // ... verify post-conditions
    verify_ServiceState_Is(serviceUnderTest, expectedValue);

    Mockito.verifyNoMoreInteractions(eventsListenerUnderTest);
  }

  // ... verify methods

  private static void verify_ServiceState_Is(final SirenOfShameSingleStateHostController service,
      final ESirenOfShameAlarmLevel expectedValue) {

    final ESirenOfShameAlarmLevel currentValue = service.getCurrentAlarmLevel();
    Assertions.assertThat(currentValue).isEqualTo(expectedValue);
  }

}
