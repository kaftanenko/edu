package app.sireneofshame.host.service.jenkins.client.http;

import static app.sireneofshame.host.service.jenkins.client.JenkinsClientConstants.AUTH_PASSWORD;
import static app.sireneofshame.host.service.jenkins.client.JenkinsClientConstants.AUTH_USERNAME;
import static app.sireneofshame.host.service.jenkins.client.JenkinsClientConstants.HOST_URL;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import app.sireneofshame.host.service.jenkins.client.json.parser.JenkinsApiJsonParser;

public class JenkinsHttpClientLauncher {

	// ... constants

	private static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/mais/job/build-jobs";

	// ... launch methods

	public static void main(final String... args) {

		final JenkinsHttpClientConfig config = JenkinsHttpClientConfig.of(HOST_URL, AUTH_USERNAME, AUTH_PASSWORD);
		final JenkinsHttpClient controller = new JenkinsHttpClient(config);

		final Map<String, Object> jsonRootNode = controller.callJsonApi(JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS);
		System.out.println(jsonRootNode);

		final Collection<Map<String, Object>> jsonJobsNode = JenkinsApiJsonParser.extractJobsNode(jsonRootNode);
		System.out.println(jsonJobsNode);

		final Set<String> jobsStatesSummary = JenkinsApiJsonParser.collectJobsStates(jsonJobsNode);
		System.out.println(jobsStatesSummary);
	}

}
