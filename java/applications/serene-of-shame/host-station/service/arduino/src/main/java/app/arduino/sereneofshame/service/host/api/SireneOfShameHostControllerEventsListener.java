package app.arduino.sereneofshame.service.host.api;

public interface SireneOfShameHostControllerEventsListener {

	// ... events handler methods

	public void onStateChanged(ESireneOfShameState from, ESireneOfShameState to);

}
