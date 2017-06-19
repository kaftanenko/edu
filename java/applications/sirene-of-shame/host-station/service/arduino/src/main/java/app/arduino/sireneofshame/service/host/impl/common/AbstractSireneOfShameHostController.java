package app.arduino.sireneofshame.service.host.impl.common;

import java.util.ArrayList;
import java.util.List;

import app.arduino.sireneofshame.service.host.api.ESireneOfShameAlarmLevel;
import app.arduino.sireneofshame.service.host.api.SireneOfShameHostController;
import app.arduino.sireneofshame.service.host.api.SireneOfShameHostControllerEventsListener;
import app.arduino.sireneofshame.service.host.api.config.SireneOfShameHostControllerConfig;

public abstract class AbstractSireneOfShameHostController implements SireneOfShameHostController {

	// ... properties

	protected final SireneOfShameHostControllerConfig configuration;

	private final List<SireneOfShameHostControllerEventsListener> eventsListeners;

	// ... constructors

	public AbstractSireneOfShameHostController(final SireneOfShameHostControllerConfig configuration) {

		this.configuration = configuration;
		this.eventsListeners = new ArrayList<>();
	}

	// ... events management methods

	protected void notifyEventsListenersAboutStateChange(final ESireneOfShameAlarmLevel from, final ESireneOfShameAlarmLevel to) {

		for (final SireneOfShameHostControllerEventsListener eventsListener : eventsListeners) {

			eventsListener.onStateChanged(from, to);
		}
	}

	@Override
	public void subscribe(final SireneOfShameHostControllerEventsListener eventsListener) {

		eventsListeners.add(eventsListener);
	}

	@Override
	public void unsubscribe(final SireneOfShameHostControllerEventsListener eventsListener) {

		eventsListeners.remove(eventsListener);
	}

}
