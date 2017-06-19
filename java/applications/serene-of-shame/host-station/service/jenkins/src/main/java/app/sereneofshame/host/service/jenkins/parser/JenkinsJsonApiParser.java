package app.sereneofshame.host.service.jenkins.parser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JenkinsJsonApiParser {

	// ... constants

	private static final String JENKINS_RESPONSE__JSON_NODE_NAME__JOBS = "jobs";
	private static final String JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__COLOR = "color";

	// ... business methods

	public static Set<String> collectJobsStates(final Collection<Map<String, Object>> jobsStateNode) {

		return jobsStateNode.stream().map( //
				e -> e.get(JENKINS_RESPONSE__JSON_ATTRIBUTE_NAME__COLOR).toString() //
		).collect(Collectors.toSet());
	}

	@SuppressWarnings("unchecked")
	public static Collection<Map<String, Object>> extractJobsNode(final Map<String, Object> rootNode) {

		return (Collection<Map<String, Object>>) rootNode.get(JENKINS_RESPONSE__JSON_NODE_NAME__JOBS);
	}

}
