package app.sereneofshame.host.service.jenkins;

import static app.sereneofshame.host.service.jenkins.JenkinsConstants.AUTH_PASSWORD;
import static app.sereneofshame.host.service.jenkins.JenkinsConstants.AUTH_USERNAME;
import static app.sereneofshame.host.service.jenkins.JenkinsConstants.HOST_URL;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import app.sereneofshame.host.service.jenkins.parser.JenkinsJsonApiParser;

public class JenkinsHttpClientLauncher {

	// ... constants

	private static final String JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS = "/job/mais/job/build-jobs";

	// ... launch methods

	public static void main(final String... args) {

		final JenkinsHttpClientConfig config = JenkinsHttpClientConfig.of(HOST_URL, AUTH_USERNAME, AUTH_PASSWORD);
		final JenkinsHttpClient controller = new JenkinsHttpClient(config);

		final Map<String, Object> jsonRootNode = controller.callJsonApi(JENKINS_RESOURCE_PATH__MAIS__BUILD_JOBS);

		final Collection<Map<String, Object>> jsonJobsNode = JenkinsJsonApiParser.extractJobsNode(jsonRootNode);
		System.out.println(jsonJobsNode);

		final Set<String> jobsStatesSummary = JenkinsJsonApiParser.collectJobsStates(jsonJobsNode);
		System.out.println(jobsStatesSummary);
	}

}
