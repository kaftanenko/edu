package app.arduino.sereneofshame.service.host.api;

public interface SireneOfShameHostController {

	// ... business methods

	public ESireneOfShameState getState();

	public void setState(final ESireneOfShameState state);

	// ... events management methods

	public void subscribe(SireneOfShameHostControllerEventsListener eventsListener);

	public void unsubscribe(SireneOfShameHostControllerEventsListener eventsListener);

}
