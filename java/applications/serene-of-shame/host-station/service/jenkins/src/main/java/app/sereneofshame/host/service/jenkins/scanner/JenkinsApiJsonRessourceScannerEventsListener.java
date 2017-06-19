package app.sereneofshame.host.service.jenkins.scanner;

import java.util.Map;

public interface JenkinsApiJsonRessourceScannerEventsListener {

	// ... events handler methods

	public void onRessourceRead(Map<String, Object> jsonRootNode);

}
