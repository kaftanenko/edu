package app.arduino.sereneofshame.service.impl.common;

import java.util.ArrayList;
import java.util.List;

import app.arduino.sereneofshame.service.api.ESireneOfShameState;
import app.arduino.sereneofshame.service.api.SireneOfShameController;
import app.arduino.sereneofshame.service.api.SireneOfShameControllerEventsListener;
import app.arduino.sereneofshame.service.api.config.SireneOfShameControllerConfig;

public abstract class AbstractSireneOfShameController implements SireneOfShameController {

	// ... properties

	protected final SireneOfShameControllerConfig configuration;

	private final List<SireneOfShameControllerEventsListener> eventsListeners;

	// ... constructors

	public AbstractSireneOfShameController(final SireneOfShameControllerConfig configuration) {

		this.configuration = configuration;
		this.eventsListeners = new ArrayList<>();
	}

	// ... events management methods

	protected void notifyEventsListenersAboutStateChange(final ESireneOfShameState from, final ESireneOfShameState to) {

		for (final SireneOfShameControllerEventsListener eventsListener : eventsListeners) {

			eventsListener.onStateChanged(from, to);
		}
	}

	@Override
	public void subscribe(final SireneOfShameControllerEventsListener eventsListener) {

		eventsListeners.add(eventsListener);
	}

	@Override
	public void unsubscribe(final SireneOfShameControllerEventsListener eventsListener) {

		eventsListeners.remove(eventsListener);
	}

}
