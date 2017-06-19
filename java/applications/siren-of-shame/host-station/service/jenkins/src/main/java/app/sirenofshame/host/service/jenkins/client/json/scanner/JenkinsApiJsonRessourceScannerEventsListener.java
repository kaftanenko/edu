package app.sirenofshame.host.service.jenkins.client.json.scanner;

import java.util.Map;

public interface JenkinsApiJsonRessourceScannerEventsListener {

	// ... events handler methods

	public void onBeforeRessourceRequest(String resourcePath);

	public void onAfterRessourceResponse(String resourcePath, Map<String, Object> jsonRootNode);

}
