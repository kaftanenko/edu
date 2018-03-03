package app.arduino.sirenofshame.service.host.impl.common;

import java.util.ArrayList;
import java.util.List;

import app.arduino.sirenofshame.service.host.api.ESirenOfShameAlarmLevel;
import app.arduino.sirenofshame.service.host.api.SirenOfShameHostController;
import app.arduino.sirenofshame.service.host.api.SirenOfShameHostControllerEventsListener;
import app.arduino.sirenofshame.service.host.api.config.SirenOfShameHostControllerConfig;

public abstract class AbstractSirenOfShameHostController //
		extends AbstractSirenOfShameCommonHostController //
		implements SirenOfShameHostController //
{

	// ... properties

	protected final SirenOfShameHostControllerConfig configuration;

	private final List<SirenOfShameHostControllerEventsListener> eventsListeners;

	// ... constructors

	public AbstractSirenOfShameHostController(final SirenOfShameHostControllerConfig configuration) {

		this.configuration = configuration;
		this.eventsListeners = new ArrayList<>();
	}

	// ... events management methods

	protected void notifyEventsListenersAboutStateChange(final ESirenOfShameAlarmLevel from,
			final ESirenOfShameAlarmLevel to) {

		for (final SirenOfShameHostControllerEventsListener eventsListener : eventsListeners) {

			eventsListener.onStateChanged(from, to);
		}
	}

	@Override
	public void subscribe(final SirenOfShameHostControllerEventsListener eventsListener) {

		eventsListeners.add(eventsListener);
	}

	@Override
	public void unsubscribe(final SirenOfShameHostControllerEventsListener eventsListener) {

		eventsListeners.remove(eventsListener);
	}

}
