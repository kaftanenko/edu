package app.arduino.sereneofshame.service.api;

public interface SireneOfShameController {

	// ... business methods

	public ESireneOfShameState getState();

	public void setState(final ESireneOfShameState state);

	// ... events management methods

	public void subscribe(SireneOfShameControllerEventsListener eventsListener);

	public void unsubscribe(SireneOfShameControllerEventsListener eventsListener);

}
