package app.sirenofshame.multistate.host.service.jenkins.client.http.scanner.mock;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScanner;
import app.sirenofshame.common.host.service.jenkins.client.http.scanner.JenkinsApiJsonResourceScannerConfig;

public class JenkinsApiJsonResourceTraversingScannerMock extends JenkinsApiJsonResourceScanner //
{

	// ... constants

	private String bodyAsString = getMockData("/jenkins-info-mock-data.json");

	// ... constructors

	public JenkinsApiJsonResourceTraversingScannerMock(final JenkinsApiJsonResourceScannerConfig config) {

		super(config);
	}

	@Override
	protected Map<String, Object> callJsonApi(final String resourcePath) {

		try {

			final Map<String, Object> jsonContentAsMap = //
					new ObjectMapper().readValue(bodyAsString, new TypeReference<Map<String, Object>>() {
					});
			return jsonContentAsMap;
		} catch (final Exception ex) {
			LOG.error(ex);
			throw new RuntimeException(ex);
		}
	}

	// ... helper methods

	private static String getMockData(String srcFilePath) {

		try {
			File file = new File(JenkinsApiJsonResourceTraversingScannerMock.class.getResource(srcFilePath).toURI());
			return FileUtils.readFileToString(file);
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
