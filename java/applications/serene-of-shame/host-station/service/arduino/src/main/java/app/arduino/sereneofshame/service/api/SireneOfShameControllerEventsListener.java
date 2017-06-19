package app.arduino.sereneofshame.service.api;

public interface SireneOfShameControllerEventsListener {

	// ... events handler methods

	public void onStateChanged(ESireneOfShameState from, ESireneOfShameState to);

}
