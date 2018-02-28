package app.arduino.sirenofshame.frontend.impl.javafx.service;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.arduino.sirenofshame.frontend.impl.javafx.exception.util.ErrorHelper;
import app.sirenofshame.host.service.jenkins.client.http.JenkinsHttpClientConfig;

public class ConfigService {

	// ... constants

	private static final String CONFIG_FILE_NAME__APP_SIRENOFSHAME_CONFIG_JSON = "app-sirenofshame-config.json";
	private static final File CONFIG_FILE = new File(CONFIG_FILE_NAME__APP_SIRENOFSHAME_CONFIG_JSON);

	// ... business methods

	public static JenkinsHttpClientConfig load() {

		try {

			final ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(CONFIG_FILE, JenkinsHttpClientConfig.class);
		} catch (final Exception ex) {
			throw ErrorHelper.handleFatalException(ex);
		}
	}

	public static void save(final JenkinsHttpClientConfig config) {

		try {

			final ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(CONFIG_FILE, config);
		} catch (final Exception ex) {
			throw ErrorHelper.handleFatalException(ex);
		}
	}

	public static void main(final String[] args) {

		final JenkinsHttpClientConfig config = JenkinsHttpClientConfig.of("111", "222", "333");
		ConfigService.save(config);
	}

}
