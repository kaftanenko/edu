package app.sirenofshame.host.service.jenkins.client.http;

import java.util.List;

public class JenkinsJobsScanPlan {

	// ... properties

	private String pageURL;

	private List<String> jobNameRegExPatterns;

	// ... getter/setter methods

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(final String pageURL) {
		this.pageURL = pageURL;
	}

	public List<String> getJobNameRegExPatterns() {
		return jobNameRegExPatterns;
	}

	public void setJobNameRegExPatterns(final List<String> jobNameRegExPatterns) {
		this.jobNameRegExPatterns = jobNameRegExPatterns;
	}

}
