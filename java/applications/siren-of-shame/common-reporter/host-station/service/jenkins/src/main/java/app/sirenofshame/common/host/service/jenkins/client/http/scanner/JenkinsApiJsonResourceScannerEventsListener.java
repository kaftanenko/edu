package app.sirenofshame.common.host.service.jenkins.client.http.scanner;

import java.util.Map;

public interface JenkinsApiJsonResourceScannerEventsListener {

	// ... events handler methods

	public void onBeforeResourceRequest(String resourcePath);

	public void onAfterResourceResponse(String resourcePath, Map<String, Object> jsonRootNode);

}
