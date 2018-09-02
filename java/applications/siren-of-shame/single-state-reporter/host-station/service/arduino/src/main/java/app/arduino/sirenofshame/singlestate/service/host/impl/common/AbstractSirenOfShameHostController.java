package app.arduino.sirenofshame.singlestate.service.host.impl.common;

import java.util.ArrayList;
import java.util.List;

import app.arduino.sirenofshame.common.service.host.SerialChannelHostController;
import app.arduino.sirenofshame.singlestate.service.host.api.SirenOfShameSingleStateHostController;
import app.arduino.sirenofshame.singlestate.service.host.api.SirenOfShameSingleStateHostControllerEventsListener;
import app.arduino.sirenofshame.singlestate.service.host.api.type.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.singlestate.service.host.api.type.SirenOfShameSingleStateHostControllerConfig;

public abstract class AbstractSirenOfShameHostController //
    extends SerialChannelHostController //
    implements SirenOfShameSingleStateHostController //
{

  // ... properties

  protected final SirenOfShameSingleStateHostControllerConfig configuration;

  private final List<SirenOfShameSingleStateHostControllerEventsListener> eventsListeners;

  // ... constructors

  public AbstractSirenOfShameHostController(final SirenOfShameSingleStateHostControllerConfig configuration) {

    super(false);

    this.configuration = configuration;
    this.eventsListeners = new ArrayList<>();
  }

  // ... events management methods

  protected void notifyEventsListenersAboutStateChange( //
      final ESirenOfShameAlarmLevel from, //
      final ESirenOfShameAlarmLevel to //
  ) {

    for (final SirenOfShameSingleStateHostControllerEventsListener eventsListener : eventsListeners) {

      eventsListener.onStateChanged(from, to);
    }
  }

  @Override
  public void subscribe(final SirenOfShameSingleStateHostControllerEventsListener eventsListener) {

    eventsListeners.add(eventsListener);
  }

  @Override
  public void unsubscribe(final SirenOfShameSingleStateHostControllerEventsListener eventsListener) {

    eventsListeners.remove(eventsListener);
  }

}
