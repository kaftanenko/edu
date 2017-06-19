package app.sereneofshame.host.service.jenkins.client.json.scanner;

import java.util.Calendar;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import app.sereneofshame.host.service.jenkins.client.JenkinsClientFactory;

public class DefaultJenkinsApiJsonRessourceScannerEventsListener
		implements JenkinsApiJsonRessourceScannerEventsListener {

	// ... dependencies

	private static final Logger LOG = JenkinsClientFactory.getLogger();

	// ... events handler methods

	@Override
	public void onBeforeRessourceRequest(final String resourcePath) {

		final String currentTime = getCurrentTimeInGMTFormat();
		LOG.info("Send Request:\n" //
				+ "{\n" //
				+ "\t currentTime: " + currentTime + ", \n" //
				+ "\t resourcePath: " + resourcePath + " \n" //
				+ "}");
	}

	@Override
	public void onAfterRessourceResponse(final String resourcePath, final Map<String, Object> jsonRootNode) {

		final String currentTime = getCurrentTimeInGMTFormat();
		LOG.info("Received Response:\n" //
				+ "{\n" //
				+ "\t currentTime: " + currentTime + ", \n" //
				+ "\t resourcePath: " + resourcePath + ", \n" //
				+ "\t responseBody: " + jsonRootNode + "\n" //
				+ "}");
	}

	// ... helper methods

	private String getCurrentTimeInGMTFormat() {

		final String currentTime = Calendar.getInstance().getTime().toGMTString();
		return currentTime;
	}

}
